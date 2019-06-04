package com.bsoft.checkappointment.outpatients.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.text.Spanned;
import android.view.View;

import com.bsoft.baselib.http.HttpEnginer;
import com.bsoft.baselib.http.exception.ApiException;
import com.bsoft.baselib.utils.RxUtil;
import com.bsoft.checkappointment.MyApplication;
import com.bsoft.checkappointment.R;
import com.bsoft.checkappointment.common.ChooseAppointTimeActivity;
import com.bsoft.checkappointment.model.PatientAppointmentVo;
import com.bsoft.checkappointment.model.SystemConfigVo;
import com.bsoft.common.adapter.CommonAdapter;
import com.bsoft.common.adapter.ViewHolder;
import com.bsoft.common.fragment.BaseLazyLoadFragment;
import com.bsoft.common.http.BaseObserver;
import com.bsoft.common.http.ResultConverter;
import com.bsoft.common.utils.ToastUtil;
import com.bsoft.common.utils.ZXingUtil;
import com.bsoft.common.view.dialog.AlertDialog;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/5/27.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
public class UnappointFragment extends BaseLazyLoadFragment {

    private List<PatientAppointmentVo> mList = new ArrayList<>();
    private PatientAppointmentVo mSelectedAppointVo;
    private CommonAdapter<PatientAppointmentVo> mAdapter;
    private boolean isNeedPayFirst;
    private String mPayTimeLimit;

    @Override
    protected void loadData() {
        HttpEnginer.newInstance()
                .addUrl("auth/sysParameter/getSysParameter")
                .addParam("parameterKey", "CA_notFeeAutoCancelTime")
                .post(new ResultConverter<SystemConfigVo>() {
                })
                .flatMap((Function<SystemConfigVo, ObservableSource<SystemConfigVo>>) systemConfigVo -> {
                    mPayTimeLimit = systemConfigVo.getParameterValue();
                    return HttpEnginer.newInstance()
                            .addUrl("auth/sysParameter/getSysParameter")
                            .addParam("parameterKey", "CA_appointmentIsFee")
                            .post(new ResultConverter<SystemConfigVo>() {
                            });
                })
                .flatMap((Function<SystemConfigVo, ObservableSource<List<PatientAppointmentVo>>>) systemConfigVo -> {
                    isNeedPayFirst = systemConfigVo.getParameterValue().equals("1");
                    return HttpEnginer.newInstance()
                            .addUrl("auth/checkAppointment/getCheckAppointmentItem")
                            .addParam("hospitalCode", MyApplication.loginUserVo.getHospitalCode())
                            .addParam("patientType", 1)
                            .addParam("patientIdentityCardType", 1)
                            .addParam("patientIdentityCardNumber", "37263819980909293X")
                            .addParam("appointmentSign", 0)
                            .post(new ResultConverter<List<PatientAppointmentVo>>() {
                            });
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
    public void initData(@Nullable Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        if (recyclerView != null) {
            mAdapter = new CommonAdapter<PatientAppointmentVo>(mContext, R.layout.recycle_item_outpatients_unappoint, mList) {

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
                            .setText(R.id.pay_state_tv, patientAppointmentVo.getFeeStatus() == 1 ? "已支付" : "未支付")
                            .setVisible(R.id.unappoint_appoint_tv, !isNeedPayFirst || patientAppointmentVo.getFeeStatus() == 1)
                            .setVisible(R.id.unappoint_pay_tv, patientAppointmentVo.getFeeStatus() == 0)
                            .setText(R.id.note_tv, noteStr);

                    holder.setOnClickListener(R.id.unappoint_appoint_tv, v -> {
                        mSelectedAppointVo = mList.get(holder.getAdapterPosition());
                        if (!isNeedPayFirst) {
                            showAppointDailog();
                        } else {
                            gotoAppoint();
                        }
                    }).setOnClickListener(R.id.unappoint_pay_tv, v -> {
                        ToastUtil.showShort("支付");
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

    private void showAppointDailog() {
        Spanned payTimeLimit = Html.fromHtml(String.format((getString(R.string.pay_time_limit)), mPayTimeLimit));
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setCancelable(true)
                .setContentView(R.layout.dialog_outpatients_appointment_note)
                .setAnimations(R.style.dialog_from_bottom_anim)
                .setText(R.id.pay_time_limit_tv, payTimeLimit.toString())
                .setOnClickeListener(R.id.dialog_appoint_continue_tv, v -> {
                    dialogBuilder.dismiss();
                    gotoAppoint();
                })
                .setOnClickeListener(R.id.dialog_appoint_pay_tv, v -> {
                    ToastUtil.showShort("立即支付");
                    dialogBuilder.dismiss();
                }).show();
    }


}
