package com.bsoft.checkappointment.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bsoft.baselib.http.HttpEnginer;
import com.bsoft.baselib.http.exception.ApiException;
import com.bsoft.baselib.utils.RxUtil;
import com.bsoft.checkappointment.Const;
import com.bsoft.checkappointment.MyApplication;
import com.bsoft.checkappointment.R;
import com.bsoft.checkappointment.model.PatientAppointmentVo;
import com.bsoft.checkappointment.model.ReAppointOrCancleCountVo;
import com.bsoft.checkappointment.model.SystemConfigVo;
import com.bsoft.common.activity.BaseActivity;
import com.bsoft.common.http.BaseObserver;
import com.bsoft.common.http.HttpBaseResultVo;
import com.bsoft.common.http.ResultConverter;
import com.bsoft.common.utils.DateUtil;
import com.bsoft.common.utils.ToastUtil;
import com.bsoft.common.view.dialog.AlertDialog;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/5/31.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
public class CancelAppointActivity extends BaseAppointOrCancleActivity {
    private String mCancelChangeAppointCountLimit;//取消改约的次数限制
    private int mCancledCount;

    @Override
    public void onPreExcuteTask() {
        super.onPreExcuteTask();
        mCancelChangeAppointCountLimit = Const.systemConfigMap.get("CA_updateTimes");
        getCancledCount();
    }

    @Override
    protected String getToolbarTitle() {
        return "取消确认";
    }

    @Override
    protected String getExcuteBtnText() {
        return "确认取消";
    }

    @Override
    protected void excuteTask() {
        showCancleDialog();
    }

    private void getCancledCount() {
        HttpEnginer.newInstance()
                .addUrl("auth/checkAppointment/getAppointmentRecordUpdateTimes")
                .addParam("hospitalCode", MyApplication.loginUserVo.getHospitalCode())
                .addParam("checkRequestNumber", mAppointVo.getCheckRequestNumber())
                .post(new ResultConverter<ReAppointOrCancleCountVo>() {
                })
                .compose(RxUtil.applyLifecycleSchedulers(this))
                .subscribe(new BaseObserver<ReAppointOrCancleCountVo>() {
                    @Override
                    public void onFail(ApiException exception) {

                    }

                    @Override
                    public void onNext(ReAppointOrCancleCountVo reAppointOrCancleCountVo) {
                        mCancledCount = reAppointOrCancleCountVo.getUpdateTimes();
                    }
                });
    }

    private void showCancleDialog() {
        String noteStr = null;
        if (Integer.parseInt(mCancelChangeAppointCountLimit) > mCancledCount) {
            if (Integer.parseInt(mCancelChangeAppointCountLimit) > 0) {
                noteStr = String.format(getString(R.string.dialog_cancel_appointment_note_with_limit), mCancelChangeAppointCountLimit, String.valueOf(mCancledCount + 1));
            } else {
                noteStr = getString(R.string.dialog_cancel_appointment_note_without_limit);
            }
            AlertDialog.Builder cancleDialogBuilder = new AlertDialog.Builder(this);
            cancleDialogBuilder.setCancelable(false)
                    .setContentView(R.layout.dialog_cancel_appointment_note)
                    .setAnimations(R.style.dialog_from_bottom_anim)
                    .setText(R.id.dialog_cancel_appointment_note_tv, noteStr)
                    .setOnClickeListener(R.id.dialog_appoint_continue_tv, v -> {
                        cancleDialogBuilder.dismiss();
                        cancleAppointment();
                    })
                    .setOnClickeListener(R.id.dialog_appoint_back_tv,
                            v -> cancleDialogBuilder.dismiss())
                    .show();
        } else {
            ToastUtil.showShort("取消次数超过限制，无法取消，请按上次预约时间检查");
        }

    }

    private void cancleAppointment() {
        HttpEnginer.newInstance()
                .addUrl("auth/checkAppointment/doCancelReservation")
                .addParam("hospitalCode", "A00001")
                .addParam("appointmentRecordId", "1559117986758R13209")
                .post()
                .compose(RxUtil.applyLifecycleLCESchedulers(this, this))
                .subscribe(new BaseObserver<String>() {
                    @Override
                    public void onFail(ApiException exception) {
                        ToastUtil.showShort(exception.getMessage());
                    }

                    @Override
                    public void onNext(String result) {
                        HttpBaseResultVo resultVo = JSON.parseObject(result, HttpBaseResultVo.class);
                        if (resultVo.code == 1 || resultVo.code == 200) {
                            //取消成功
                            Intent intent = new Intent(CancelAppointActivity.this, AppointOrCancleResultActivity.class);
                            intent.putExtra("opType", 2);//取消预约操作
                            startActivity(intent);
                            CancelAppointActivity.this.finish();
                        } else {
                            ToastUtil.showShort("取消预约失败，请按原来预约时间进行检查");
                        }
                    }
                });
    }


}
