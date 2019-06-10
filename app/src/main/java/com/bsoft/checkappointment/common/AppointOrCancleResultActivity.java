package com.bsoft.checkappointment.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.bsoft.baselib.framework.helper.EventBusManager;
import com.bsoft.checkappointment.CheckAppointConfig;
import com.bsoft.checkappointment.Const;
import com.bsoft.checkappointment.R;
import com.bsoft.checkappointment.callback.OnSelectPatientListener;
import com.bsoft.checkappointment.callback.SelectPatientCallback;
import com.bsoft.checkappointment.event.GoToAppointmentEvent;
import com.bsoft.checkappointment.model.PatientInfo;
import com.bsoft.checkappointment.outpatients.activity.OutPatientsAppointActivity;
import com.bsoft.common.activity.BaseActivity;
import com.bsoft.common.view.roundview.RoundTextView;

import org.greenrobot.eventbus.EventBus;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/6/5.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
public class AppointOrCancleResultActivity extends BaseActivity {
    private TextView mSuccessTitleMsgTv;
    private int mOpType;  //1.预约 2.取消

    @Override
    public int getContentViewId(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_appoint_or_cancle_success;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mOpType = getIntent().getIntExtra("opType", 1);
        mSuccessTitleMsgTv = findViewById(R.id.appoint_or_cancle_success_title_tv);
        if (mOpType == 1) {
            initDefaultToolbar("预约成功");
            mSuccessTitleMsgTv.setText("恭喜！预约成功");
        } else if (mOpType == 2) {
            initDefaultToolbar("取消成功");
            mSuccessTitleMsgTv.setText("恭喜！取消成功");
        }

        findViewById(R.id.goto_appointment_rtv).setOnClickListener(v -> {
            if (Const.patientType == 1) {
                EventBus.getDefault().post(GoToAppointmentEvent.OUTPATIENT);
                startActivity(new Intent(AppointOrCancleResultActivity.this, OutPatientsAppointActivity.class));
            } else if (Const.patientType == 2) {
                EventBus.getDefault().post(GoToAppointmentEvent.INPATIENT);
                startActivity(new Intent(AppointOrCancleResultActivity.this, OutPatientsAppointActivity.class));
            }

        });


    }
}
