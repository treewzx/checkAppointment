package com.bsoft.checkappointment.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bsoft.baselib.http.HttpEnginer;
import com.bsoft.baselib.http.exception.ApiException;
import com.bsoft.baselib.utils.RxUtil;
import com.bsoft.checkappointment.R;
import com.bsoft.checkappointment.model.AppointTimeVo;
import com.bsoft.checkappointment.model.PatientAppointmentVo;
import com.bsoft.common.activity.BaseActivity;
import com.bsoft.common.http.BaseObserver;
import com.bsoft.common.http.HttpBaseResultVo;
import com.bsoft.common.utils.DateUtil;
import com.bsoft.common.utils.ToastUtil;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/6/5.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
//重新预约确认页面
public class ReAppointSureActivity extends BaseActivity {

    protected PatientAppointmentVo mPreviousAppointVo;
    private AppointTimeVo mAppointTimeVo;

    private LinearLayout mPatientDepartmentll;
    private LinearLayout mPatientBedNumll;

    private TextView mPatientInfoTv;
    private TextView mPatientNoTitleTv;
    private TextView mPatientNoTv;
    private TextView mPatientDepartmentTv;
    private TextView mPatientBedNumTv;
    private TextView mPatientCheckItemTv;
    private TextView mPreviousCheckTimeTv;
    private TextView mPreviousCheckLocationTv;
    private TextView mNewCheckTimeTv;
    private TextView mNewCheckLocationTv;
    private TextView mPatientCheckNoteTv;
    private TextView mAppointExcuteTv;


    @Override
    public int getContentViewId(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_reappoint_sure;
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
        mPreviousCheckTimeTv = findViewById(R.id.previous_appoint_time_tv);
        mPreviousCheckLocationTv = findViewById(R.id.previous_appoint_location_tv);
        mNewCheckTimeTv = findViewById(R.id.new_appoint_time_tv);
        mNewCheckLocationTv = findViewById(R.id.new_appoint_location_tv);
        mPatientCheckNoteTv = findViewById(R.id.check_note_tv);
        mAppointExcuteTv = findViewById(R.id.confirm_or_cancel_appoint_tv);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        String checkTime = null;
        initDefaultToolbar("重约确认");
        initView();
        mAppointExcuteTv.setText("重新预约");
        mPreviousAppointVo = getIntent().getParcelableExtra("appointmentItem");
        mAppointTimeVo = getIntent().getParcelableExtra("appointTime");

        if (mPreviousAppointVo.getPatientType() == 3) {
            mPatientDepartmentll.setVisibility(View.VISIBLE);
            mPatientBedNumll.setVisibility(View.VISIBLE);
            mPatientNoTitleTv.setText("住院号码");
            mPatientBedNumTv.setText(mPreviousAppointVo.getBedNumber());
        } else {
            mPatientDepartmentll.setVisibility(View.GONE);
            mPatientBedNumll.setVisibility(View.GONE);
            mPatientNoTitleTv.setText("门诊号码");
        }
        mPatientInfoTv.setText(Html.fromHtml(String.format((getString(R.string.patient_info)), mPreviousAppointVo.getPatientName(), mPreviousAppointVo.getPatientAge())));
        mPatientDepartmentTv.setText(mPreviousAppointVo.getAppointmentDepartmentName());
        //mPatientBedNumTv.setText(mAppointVo.get);
        mPatientNoTv.setText(mPreviousAppointVo.getPatientNumber());
        mPatientCheckItemTv.setText(mPreviousAppointVo.getCheckItemName());

        String previousCheckTime = new StringBuilder()
                .append(DateUtil.getYMDHM(mPreviousAppointVo.getCheckStartTime()))
                .append("-")
                .append(DateUtil.getHM(mPreviousAppointVo.getCheckEndTime()))
                .toString();

        String newCheckTime = new StringBuilder()
                .append(DateUtil.getYMDHM(mAppointTimeVo.getNumberStartTime()))
                .append("-")
                .append(DateUtil.getHM(mAppointTimeVo.getNumberEndTime()))
                .toString();

        mPreviousCheckTimeTv.setText(previousCheckTime);
        mPreviousCheckLocationTv.setText(mPreviousAppointVo.getCheckAddress());
        mNewCheckTimeTv.setText(newCheckTime);
        mNewCheckLocationTv.setText(mPreviousAppointVo.getCheckAddress());
        mPatientCheckNoteTv.setText(mPreviousAppointVo.getMattersNeedingAttention());

        mAppointExcuteTv.setOnClickListener(v -> {
            reAppoint();
        });
    }


    private void reAppoint() {

        HttpEnginer.newInstance()
                .addUrl("auth/checkAppointment/reAppointment")
                .addParam("hospitalCode", "A00001")
                .addParam("checkRequestNumber", "NO1559117412979R17876")
                .addParam("checkItemCode", "MRI")
                .addParam("checkItemName", "MRI")
                .addParam("appointmentDate", "2019-06-01")
                .addParam("appointmentQueueCode", "A01")
                .addParam("numberStartTime", "2019-06-01 09:00:00")
                .addParam("numberEndTime", "2019-06-01 10:00:00")
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
                            Intent intent = new Intent(ReAppointSureActivity.this, AppointOrCancleResultActivity.class);
                            intent.putExtra("opType", 1);//预约
                            startActivity(intent);
                            ReAppointSureActivity.this.finish();
                        } else {
                            ToastUtil.showShort(resultVo.msg);
                        }
                    }
                });
    }
}
