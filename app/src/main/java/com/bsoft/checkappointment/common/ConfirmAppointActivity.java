package com.bsoft.checkappointment.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bsoft.checkappointment.R;
import com.bsoft.checkappointment.model.AppointTimeVo;
import com.bsoft.checkappointment.model.PatientAppointmentVo;
import com.bsoft.common.activity.BaseActivity;
import com.bsoft.common.utils.DateUtil;

import java.util.Date;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/5/31.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
public class ConfirmAppointActivity extends BaseActivity {
    private PatientAppointmentVo mAppointVo;
    private AppointTimeVo mAppointTimeVo;

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
    private TextView mAppointExcuteTv;


    @Override
    public int getContentViewId(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_confirm_or_cancel_appoint;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initDefaultToolbar("预约确认");
        initView();
        mAppointVo = getIntent().getParcelableExtra("appointmentItem");
        mAppointTimeVo = getIntent().getParcelableExtra("appointTime");

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
                .append(DateUtil.getYMDHM(mAppointTimeVo.getNumberStartTime()))
                .append("-")
                .append(DateUtil.getHM(mAppointTimeVo.getNumberEndTime()))
                .toString();
        mPatientCheckTimeTv.setText(checkTime);
        mPatientCheckLocationTv.setText(mAppointVo.getCheckAddress());
        mPatientCheckNoteTv.setText(mAppointVo.getMattersNeedingAttention());

        mAppointExcuteTv.setOnClickListener(v -> {

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
        mAppointExcuteTv = findViewById(R.id.confirm_or_cancel_appoint_tv);
    }
}
