package com.bsoft.checkappointment.inpatients.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.text.Spanned;

import com.bsoft.baselib.http.HttpEnginer;
import com.bsoft.baselib.http.exception.ApiException;
import com.bsoft.baselib.utils.RxUtil;
import com.bsoft.checkappointment.Const;
import com.bsoft.checkappointment.MyApplication;
import com.bsoft.checkappointment.R;
import com.bsoft.checkappointment.common.ChooseAppointTimeActivity;
import com.bsoft.checkappointment.event.GoToAppointmentEvent;
import com.bsoft.checkappointment.model.PatientAppointmentVo;
import com.bsoft.common.adapter.CommonAdapter;
import com.bsoft.common.adapter.ViewHolder;
import com.bsoft.common.fragment.BaseLazyLoadFragment;
import com.bsoft.common.http.BaseObserver;
import com.bsoft.common.http.ResultConverter;
import com.bsoft.common.utils.ToastUtil;
import com.bsoft.common.utils.ZXingUtil;
import com.bsoft.common.view.dialog.AlertDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/5/27.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
public class InPatientUnappointFragment extends BaseLazyLoadFragment {

    private List<PatientAppointmentVo> mList = new ArrayList<>();
    private PatientAppointmentVo mSelectedAppointVo;
    private CommonAdapter<PatientAppointmentVo> mAdapter;

    @Override
    protected void loadData() {
        HttpEnginer.newInstance()
                .addUrl("auth/checkAppointment/getCheckAppointmentItem")
                .addParam("hospitalCode", MyApplication.loginUserVo.getHospitalCode())
                .addParam("patientType", 3)
                .addParam("patientIdentityCardType", 1)
                .addParam("patientIdentityCardNumber", "37263819980909293X")
                .addParam("appointmentSign", 0)
                .post(new ResultConverter<List<PatientAppointmentVo>>() {
                })
                .compose(RxUtil.applyLifecycleLCESchedulers(this, this))
                .subscribe(new BaseObserver<List<PatientAppointmentVo>>() {
                    @Override
                    public void onFail(ApiException exception) {
                        showError();
                        ToastUtil.showShort(exception.getMessage());
                    }

                    @Override
                    public void onNext(List<PatientAppointmentVo> patientAppointmentVos) {
                        if (patientAppointmentVos != null && patientAppointmentVos.size() > 0) {
                            mList.clear();
                            mList.addAll(patientAppointmentVos);
                            mAdapter.notifyDataSetChanged();
                        } else {
                            mLoadViewHelper.showEmpty(mOnRefreshListener);
                        }
                    }
                });
    }

    @Override
    public int getContentViewId(@Nullable Bundle savedInstanceState) {
        return R.layout.fragment_patients_appoint;
    }

    @Override
    public boolean useEventBus() {
        return true;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        if (recyclerView != null) {
            mAdapter = new CommonAdapter<PatientAppointmentVo>(mContext, R.layout.recycle_item_patients_unappoint, mList) {

                @Override
                protected void convert(ViewHolder holder, PatientAppointmentVo patientAppointmentVo, int position) {
                    String requestStr = patientAppointmentVo.getCheckRequestNumber().substring(2);
                    Bitmap requestNoBitmap = ZXingUtil.getBarCodeWithoutPadding(mContext, requestStr, R.dimen.dp_330, R.dimen.dp_330, R.dimen.dp_55);
                    Spanned requestNo = Html.fromHtml(String.format((getString(R.string.request_no)), patientAppointmentVo.getCheckRequestNumber()));
                    Spanned patientInfo = Html.fromHtml(String.format((getString(R.string.patient_info)), patientAppointmentVo.getPatientName(), patientAppointmentVo.getPatientAge()));
                    Spanned appointmentItem = Html.fromHtml(String.format((getString(R.string.appointment_item)), patientAppointmentVo.getCheckItemName()));
                    Spanned noteStr = Html.fromHtml(String.format((getString(R.string.notes)), patientAppointmentVo.getMattersNeedingAttention()));

                    holder.setImageBitmap(R.id.uncheck_request_no_iv, requestNoBitmap)
                            .setText(R.id.uncheck_request_no_tv, requestNo)
                            .setText(R.id.patient_info_tv, patientInfo)
                            .setText(R.id.appointment_item_tv, appointmentItem)
                            .setVisible(R.id.pay_state_tv, false)
                            .setVisible(R.id.unappoint_appoint_tv, true)
                            .setVisible(R.id.unappoint_pay_tv, false)
                            .setText(R.id.note_tv, noteStr);

                    holder.setOnClickListener(R.id.unappoint_appoint_tv, v -> {
                        mSelectedAppointVo = mList.get(holder.getAdapterPosition());
                        gotoAppoint();
                    });
                }
            };
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            recyclerView.setAdapter(mAdapter);
        }
    }

    private void gotoAppoint() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), ChooseAppointTimeActivity.class);
        intent.putExtra("appointmentItem", mSelectedAppointVo);
        startActivity(intent);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshEvent(GoToAppointmentEvent goToAppointmentEvent) {
        if (GoToAppointmentEvent.INPATIENT == goToAppointmentEvent) {
            loadData();
        }
    }


}
