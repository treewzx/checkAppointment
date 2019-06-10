package com.bsoft.checkappointment.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.bsoft.checkappointment.CheckAppointConfig;
import com.bsoft.checkappointment.R;
import com.bsoft.checkappointment.callback.OnSelectPatientListener;
import com.bsoft.checkappointment.callback.SelectPatientCallback;
import com.bsoft.checkappointment.model.PatientInfo;
import com.bsoft.common.activity.BaseActivity;

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




    }
}
