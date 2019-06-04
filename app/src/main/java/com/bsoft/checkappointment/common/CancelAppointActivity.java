package com.bsoft.checkappointment.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.bsoft.checkappointment.R;
import com.bsoft.common.activity.BaseActivity;

import butterknife.BindView;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/5/31.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
public class CancelAppointActivity extends BaseActivity {
    @BindView(R.id.confirm_or_cancel_appoint_tv)
    TextView  mCancelAppointTv;
    @Override
    public int getContentViewId(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_confirm_or_cancel_appoint;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initDefaultToolbar("取消确认");
        mCancelAppointTv.setText("确认取消");
    }
}
