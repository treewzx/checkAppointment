package com.bsoft.checkappointment.common;

import android.content.Intent;

import com.alibaba.fastjson.JSON;
import com.bsoft.baselib.http.HttpEnginer;
import com.bsoft.baselib.http.exception.ApiException;
import com.bsoft.baselib.utils.RxUtil;
import com.bsoft.checkappointment.Const;
import com.bsoft.checkappointment.MyApplication;
import com.bsoft.checkappointment.R;
import com.bsoft.checkappointment.model.PatientAppointmentVo;
import com.bsoft.checkappointment.model.ReAppointOrCancleCountVo;
import com.bsoft.common.http.BaseObserver;
import com.bsoft.common.http.HttpBaseResultVo;
import com.bsoft.common.http.ResultConverter;
import com.bsoft.common.utils.ToastUtil;
import com.bsoft.common.view.dialog.AlertDialog;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/5/31.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
//重新预约准备页面
public class PrepareReAppointActivity extends BaseAppointOrCancleActivity {
    private String mCancelChangeAppointCountLimit;//取消改约的次数限制
    private int mReAppointCount;

    @Override
    public void onPreExcuteTask() {
        super.onPreExcuteTask();
        mCancelChangeAppointCountLimit = Const.systemConfigMap.get("CA_updateTimes");
        getReAppointCount();
    }

    @Override
    protected String getToolbarTitle() {
        return "重新预约";
    }

    @Override
    protected String getExcuteBtnText() {
        return "重新预约";
    }

    @Override
    protected void excuteTask() {
        showReAppointDialog();
    }

    private void getReAppointCount() {
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
                        mReAppointCount = reAppointOrCancleCountVo.getUpdateTimes();
                    }
                });
    }

    private void showReAppointDialog() {
        String noteStr = null;
        if (Integer.parseInt(mCancelChangeAppointCountLimit) > mReAppointCount) {
            if (Integer.parseInt(mCancelChangeAppointCountLimit) > 0) {
                noteStr = String.format(getString(R.string.dialog_change_appointment_note_with_limit), mCancelChangeAppointCountLimit, String.valueOf(mReAppointCount + 1));
            } else {
                noteStr = "是否确认改约？";
            }
            AlertDialog.Builder cancleDialogBuilder = new AlertDialog.Builder(this);
            cancleDialogBuilder.setCancelable(false)
                    .setContentView(R.layout.dialog_cancel_appointment_note)
                    .setAnimations(R.style.dialog_from_bottom_anim)
                    .setText(R.id.dialog_cancel_appointment_note_tv, noteStr)
                    .setOnClickeListener(R.id.dialog_appoint_continue_tv, v -> {
                        cancleDialogBuilder.dismiss();
                        gotoReAppointment();
                    })
                    .setOnClickeListener(R.id.dialog_appoint_back_tv,
                            v -> cancleDialogBuilder.dismiss())
                    .show();
        } else {
            ToastUtil.showShort("改约次数超过限制，无法改约，请按上次预约时间检查");
        }

    }
    private void gotoReAppointment() {
        Intent intent = new Intent();
        intent.setClass(this, ChooseAppointTimeActivity.class);
        intent.putExtra("appointmentItem", mAppointVo)
                .putExtra("isReAppoint", true);
        startActivity(intent);
    }




}
