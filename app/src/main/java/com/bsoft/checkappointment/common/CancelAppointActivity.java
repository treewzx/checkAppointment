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

import butterknife.BindView;

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


    @Override
    public int getContentViewId(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_confirm_or_cancel_appoint;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initDefaultToolbar("预约确认");
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
}
