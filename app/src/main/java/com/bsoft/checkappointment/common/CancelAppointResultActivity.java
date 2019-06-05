package com.bsoft.checkappointment.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.bsoft.checkappointment.R;
import com.bsoft.common.activity.BaseActivity;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/6/5.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
public class CancelAppointResultActivity extends BaseActivity {
    private TextView mSuccessTitleMsgTv;

    @Override
    public int getContentViewId(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_appoint_or_cancle_success;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initDefaultToolbar("取消成功");
        mSuccessTitleMsgTv = findViewById(R.id.appoint_or_cancle_success_title_tv);
        mSuccessTitleMsgTv.setText("恭喜！取消成功");


    }
}
