package com.bsoft.checkappointment.outpatients.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PathEffect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.text.Spanned;
import android.util.ArrayMap;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;

import com.bsoft.baselib.http.HttpEnginer;
import com.bsoft.baselib.http.exception.ApiException;
import com.bsoft.baselib.utils.RxUtil;
import com.bsoft.checkappointment.MyApplication;
import com.bsoft.checkappointment.R;
import com.bsoft.checkappointment.common.CancelAppointActivity;
import com.bsoft.checkappointment.common.ChooseAppointTimeActivity;
import com.bsoft.checkappointment.model.PatientAppointmentVo;
import com.bsoft.checkappointment.model.SignQueueVo;
import com.bsoft.checkappointment.model.SystemConfigVo;
import com.bsoft.common.adapter.CommonAdapter;
import com.bsoft.common.adapter.ViewHolder;
import com.bsoft.common.fragment.BaseLazyLoadFragment;
import com.bsoft.common.http.BaseObserver;
import com.bsoft.common.http.ResultConverter;
import com.bsoft.common.utils.DateUtil;
import com.bsoft.common.utils.ToastUtil;
import com.bsoft.common.utils.ZXingUtil;

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
public class AppointedFragment extends BaseLazyLoadFragment {
    private List<PatientAppointmentVo> mList = new ArrayList<>();
    private PatientAppointmentVo mSelectedAppointVo;
    private SparseArray<SignQueueVo> mSignQueueVoArray = new SparseArray<>();
    private CommonAdapter<PatientAppointmentVo> mAdapter;
    private String mSignDistanceLimit;
    private String mSignTimeLimit;

    @Override
    public int getContentViewId(@Nullable Bundle savedInstanceState) {
        return R.layout.fragment_patients_appoint;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        if (recyclerView != null) {
            mAdapter = new CommonAdapter<PatientAppointmentVo>(mContext, R.layout.recycle_item_outpatients_appointed, mList) {

                @Override
                protected void convert(ViewHolder holder, PatientAppointmentVo patientAppointmentVo, int position) {
                    String requestStr = patientAppointmentVo.getCheckRequestNumber().substring(2);
                    Bitmap requestNoBitmap = ZXingUtil.getBarCodeWithoutPadding(mContext, requestStr, R.dimen.dp_330, R.dimen.dp_330, R.dimen.dp_55);
                    Spanned requestNo = Html.fromHtml(String.format((getString(R.string.request_no)), patientAppointmentVo.getCheckRequestNumber()));
                    Spanned patientInfo = Html.fromHtml(String.format((getString(R.string.patient_info)), patientAppointmentVo.getPatientName(), patientAppointmentVo.getPatientAge()));
                    Spanned appointmentItem = Html.fromHtml(String.format((getString(R.string.appointment_item)), patientAppointmentVo.getCheckItemName()));
                    String checkTime = new StringBuilder()
                            .append(DateUtil.getYMDHM(patientAppointmentVo.getCheckStartTime()))
                            .append("-")
                            .append(DateUtil.getHM(patientAppointmentVo.getCheckEndTime()))
                            .toString();
                    Spanned appointmentTime = Html.fromHtml(String.format((getString(R.string.appointment_time)), checkTime));
                    Spanned checkAdress = Html.fromHtml(String.format((getString(R.string.check_address)), patientAppointmentVo.getCheckAddress()));
                    Spanned noteStr = Html.fromHtml(String.format((getString(R.string.notes)), patientAppointmentVo.getMattersNeedingAttention()));
                    //签到之后所有按键都不显示，需要调用排队的接口
                    holder.setImageBitmap(R.id.check_request_no_iv, requestNoBitmap)
                            .setText(R.id.appointment_request_no_tv, requestNo)
                            .setText(R.id.patient_info_tv, patientInfo)
                            .setText(R.id.appointment_item_tv, appointmentItem)
                            .setText(R.id.appointment_time_tv, appointmentTime)
                            .setText(R.id.check_address_tv, checkAdress)
                            .setText(R.id.pay_state_tv, patientAppointmentVo.getFeeStatus() == 1 ? "已支付" : "未支付")
                            .setVisible(R.id.appointment_pay_tv, patientAppointmentVo.getFeeStatus() == 0 || patientAppointmentVo.getSignInSign() == 0)
                            .setVisible(R.id.appointment_sign_tv, patientAppointmentVo.getSignInSign() == 0)
                            .setVisible(R.id.appointment_change_tv, patientAppointmentVo.getSignInSign() == 0)
                            .setVisible(R.id.appointment_cancel_tv, patientAppointmentVo.getSignInSign() == 0)
                            .setVisible(R.id.sign_sequence_no_ll, patientAppointmentVo.getSignInSign() == 1)
                            .setText(R.id.note_tv, noteStr);
                    if (patientAppointmentVo.getSignInSign() == 1 && mSignQueueVoArray.get(position).getSerialNumber() != null) {
                        Spanned currentNo = Html.fromHtml(String.format((getString(R.string.sign_current_no)), mSignQueueVoArray.get(position).getCurrentNumber()));
                        Spanned mineNo = Html.fromHtml(String.format((getString(R.string.sign_sequence_no)), mSignQueueVoArray.get(position).getSerialNumber()));
                        holder.setText(R.id.sign_current_no_tv, currentNo)
                                .setText(R.id.sign_sequence_no_tv, mineNo)
                                .setVisible(R.id.buttons, false);
                    }

                    holder.setOnClickListener(R.id.appointment_cancel_tv, v -> {
                        mSelectedAppointVo = mList.get(holder.getAdapterPosition());
                        gotoCancelAppointment();
                    }).setOnClickListener(R.id.appointment_sign_tv, v -> {
                        long timeGapMin = DateUtil.getTimeGap(patientAppointmentVo.getCheckStartTime()) / (1000 * 60);
                        if (timeGapMin < Long.parseLong(mSignTimeLimit)) {
                            ToastUtil.showShort("签到");
                        }

                    }).setOnClickListener(R.id.appointment_change_tv, v -> {

                    });
                }
            };
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            recyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    protected void loadData() {
        HttpEnginer.newInstance()
                .addUrl("auth/sysParameter/getSysParameter")
                .addParam("parameterKey", "CA_appSignInDistance")
                .post(new ResultConverter<SystemConfigVo>() {
                })
                .flatMap((Function<SystemConfigVo, ObservableSource<SystemConfigVo>>) systemConfigVo -> {
                    mSignDistanceLimit = systemConfigVo.getParameterValue();
                    Log.e("TAG", "签到距离" + mSignDistanceLimit);
                    return HttpEnginer.newInstance()
                            .addUrl("auth/sysParameter/getSysParameter")
                            .addParam("parameterKey", "CA_signInLeadTime")
                            .post(new ResultConverter<SystemConfigVo>() {
                            });
                })
                .flatMap((Function<SystemConfigVo, ObservableSource<List<PatientAppointmentVo>>>) systemConfigVo -> {
                    mSignTimeLimit = systemConfigVo.getParameterValue();
                    Log.e("TAG", "签到时间" + mSignTimeLimit);
                    return HttpEnginer.newInstance()
                            .addUrl("auth/checkAppointment/getCheckAppointmentItem")
                            .addParam("hospitalCode", MyApplication.loginUserVo.getHospitalCode())
                            .addParam("patientType", 1)
                            .addParam("patientIdentityCardType", 1)
                            .addParam("patientIdentityCardNumber", "37263819980909293X")
                            .addParam("appointmentSign", 1)
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
                            for (int i = 0; i < mList.size(); i++) {
                                mSignQueueVoArray.put(i, new SignQueueVo());
                                if (mList.get(i).getSignInSign() == 1) {
                                    //已签到，获取排号
                                    getSignQuenceInfo(mList.get(i).getAppointmentRecordId(), i);
                                }
                            }
                            mAdapter.notifyDataSetChanged();


                        } else {
                            mLoadViewHelper.showEmpty(mOnRefreshListener);
                        }
                    }
                });
    }

    public void getSignQuenceInfo(String appointmentRecordId, final int position) {
        HttpEnginer.newInstance()
                .addUrl("auth/checkAppointment/getQueueInfo")
                .addParam("hospitalCode", MyApplication.loginUserVo.getHospitalCode())
                .addParam("appointmentRecordId", appointmentRecordId)
                .post(new ResultConverter<SignQueueVo>() {
                })
                .compose(RxUtil.applyLifecycleSchedulers(this))
                .subscribe(new BaseObserver<SignQueueVo>() {
                    @Override
                    public void onFail(ApiException exception) {

                    }

                    @Override
                    public void onNext(SignQueueVo signQueueVo) {
                        mSignQueueVoArray.put(position, signQueueVo);
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void gotoCancelAppointment() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), CancelAppointActivity.class);
        intent.putExtra("appointmentItem", mSelectedAppointVo);
        startActivity(intent);
    }

}
