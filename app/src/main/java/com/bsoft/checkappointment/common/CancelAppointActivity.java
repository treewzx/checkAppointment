package com.bsoft.checkappointment.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bsoft.baselib.http.HttpEnginer;
import com.bsoft.baselib.http.exception.ApiException;
import com.bsoft.baselib.utils.RxUtil;
import com.bsoft.checkappointment.MyApplication;
import com.bsoft.checkappointment.R;
import com.bsoft.checkappointment.model.AppointTimeVo;
import com.bsoft.checkappointment.model.PatientAppointmentVo;
import com.bsoft.checkappointment.model.SystemConfigVo;
import com.bsoft.common.activity.BaseActivity;
import com.bsoft.common.http.BaseObserver;
import com.bsoft.common.http.HttpBaseResultVo;
import com.bsoft.common.http.ResultConverter;
import com.bsoft.common.utils.DateUtil;
import com.bsoft.common.utils.ToastUtil;
import com.bsoft.common.view.dialog.AlertDialog;

import butterknife.BindView;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/5/31.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
public class CancelAppointActivity extends BaseActivity {
    private PatientAppointmentVo mAppointVo;

    private LinearLayout mPatientDepartmentll;
    private LinearLayout mPatientBedNumll;

    private TextView mPatientInfoTv;
    private TextView mPatientNoTitleTv;
    private TextView mPatientNoTv;
    private TextView mPatientDepartmentTv;
    private TextView mPatientBedNumTv;
    private TextView mPatientCheckItemTv;
    private TextView mPatientCheckTimeTv;
    private TextView mPatientCheckLocationTv;
    private TextView mPatientCheckNoteTv;
    private TextView mAppointCancelTv;

    private String mCancelChangeAppointCountLimit;//取消改约的次数限制


    @Override
    public int getContentViewId(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_confirm_or_cancel_appoint;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initDefaultToolbar("取消确认");
        getCancleCountLimit();
        initView();
        mAppointCancelTv.setText("确认取消");
        mAppointVo = getIntent().getParcelableExtra("appointmentItem");

        if (mAppointVo.getPatientType() == 3) {
            mPatientDepartmentll.setVisibility(View.VISIBLE);
            mPatientBedNumll.setVisibility(View.VISIBLE);
            mPatientNoTitleTv.setText("住院号码");
        } else {
            mPatientDepartmentll.setVisibility(View.GONE);
            mPatientBedNumll.setVisibility(View.GONE);
            mPatientNoTitleTv.setText("门诊号码");
        }
        mPatientInfoTv.setText(Html.fromHtml(String.format((getString(R.string.patient_info)), mAppointVo.getPatientName(), mAppointVo.getPatientAge())));
        mPatientDepartmentTv.setText(mAppointVo.getAppointmentDepartmentName());
        //mPatientBedNumTv.setText(mAppointVo.get);
        mPatientNoTv.setText(mAppointVo.getPatientNumber());
        mPatientCheckItemTv.setText(mAppointVo.getCheckItemName());
        String checkTime = new StringBuilder()
                .append(DateUtil.getYMDHM(mAppointVo.getCheckStartTime()))
                .append("-")
                .append(DateUtil.getHM(mAppointVo.getCheckEndTime()))
                .toString();
        mPatientCheckTimeTv.setText(checkTime);
        mPatientCheckLocationTv.setText(mAppointVo.getCheckAddress());
        mPatientCheckNoteTv.setText(mAppointVo.getMattersNeedingAttention());

        mAppointCancelTv.setOnClickListener(v -> {
            showCancleDialog();
        });

    }

    private void initView() {
        mPatientDepartmentll = findViewById(R.id.patient_department_ll);
        mPatientBedNumll = findViewById(R.id.patient_bed_num_ll);
        mPatientInfoTv = findViewById(R.id.patient_info_tv);
        mPatientNoTitleTv = findViewById(R.id.patient_no_title_tv);
        mPatientNoTv = findViewById(R.id.patient_no_tv);
        mPatientDepartmentTv = findViewById(R.id.patient_department_tv);
        mPatientBedNumTv = findViewById(R.id.patient_bed_num_tv);
        mPatientCheckItemTv = findViewById(R.id.patient_check_item_tv);
        mPatientCheckTimeTv = findViewById(R.id.patient_check_time_tv);
        mPatientCheckLocationTv = findViewById(R.id.patient_check_location_tv);
        mPatientCheckNoteTv = findViewById(R.id.check_note_tv);
        mAppointCancelTv = findViewById(R.id.confirm_or_cancel_appoint_tv);
    }

    private void showCancleDialog() {
        AlertDialog.Builder cancleDialogBuilder = new AlertDialog.Builder(this);
        cancleDialogBuilder.setCancelable(false)
                .setContentView(R.layout.dialog_cancel_appointment_note)
                .setAnimations(R.style.dialog_from_bottom_anim)
                .setText(R.id.dialog_cancel_appointment_note_tv, String.format((getString(R.string.dialog_cancel_appointment_note)), mCancelChangeAppointCountLimit, "1"))
                .setOnClickeListener(R.id.dialog_appoint_continue_tv, v -> {
                    cancleDialogBuilder.dismiss();
                    cancleAppointment();

                })
                .setOnClickeListener(R.id.dialog_appoint_back_tv,
                        v -> cancleDialogBuilder.dismiss())
                .show();
    }

    private void getCancleCountLimit() {
        HttpEnginer.newInstance()
                .addUrl("auth/sysParameter/getSysParameter")
                .addParam("parameterKey", "CA_updateTimes")
                .post(new ResultConverter<SystemConfigVo>() {
                })
                .compose(RxUtil.applyLifecycleSchedulers(this))
                .subscribe(new BaseObserver<SystemConfigVo>() {
                    @Override
                    public void onFail(ApiException exception) {
                        ToastUtil.showShort(exception.getMessage());
                    }

                    @Override
                    public void onNext(SystemConfigVo systemConfigVo) {
                        mCancelChangeAppointCountLimit = systemConfigVo.getParameterValue();
                    }
                });
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
                            Intent intent = new Intent(CancelAppointActivity.this, CancelAppointResultActivity.class);
                            startActivity(intent);
                            CancelAppointActivity.this.finish();
                        } else {
                            ToastUtil.showShort("取消预约失败，请按原来预约时间进行检查");
                        }

                    }
                });
    }
}
