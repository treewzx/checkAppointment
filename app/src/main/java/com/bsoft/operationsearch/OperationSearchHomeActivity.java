package com.bsoft.operationsearch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.barnettwong.dragfloatactionbuttonlibrary.view.DragFloatActionButton;
import com.bsoft.checkappointment.R;
import com.bsoft.operationsearch.fragment.OperationCurrentFragment;
import com.bsoft.operationsearch.fragment.OperationHistoryFragment;
import com.bsoft.common.activity.BaseActivity;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/6/10.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
public class OperationSearchHomeActivity extends BaseActivity {
    private DragFloatActionButton mSwitchBtn;
    private Fragment currentFragment;
    private boolean isCurrentOp = true;

    @Override
    public int getContentViewId(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_operation_search;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initDefaultToolbar("手术查询");
        mSwitchBtn = findViewById(R.id.switch_btn);
        OperationCurrentFragment operationCurrentFragment = new OperationCurrentFragment();
        OperationHistoryFragment operationHistoryFragment = new OperationHistoryFragment();
        setDefaultFragment(operationCurrentFragment);
        mSwitchBtn.setOnClickListener(v -> {
            if (isCurrentOp) {
                isCurrentOp = false;
                mSwitchBtn.setImageResource(R.drawable.op_current_btn);
                switchFragment(operationHistoryFragment);
            } else {
                isCurrentOp = true;
                mSwitchBtn.setImageResource(R.drawable.op_history_btn);
                switchFragment(operationCurrentFragment);
            }
        });
    }


    private void switchFragment(Fragment targetFragment) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        if (!targetFragment.isAdded()) {
            transaction.hide(currentFragment)
                    .add(R.id.container, targetFragment)
                    .commit();
        } else {
            transaction.hide(currentFragment)
                    .show(targetFragment)
                    .commit();
        }
        currentFragment = targetFragment;
    }

    private void setDefaultFragment(Fragment fragment) {
        currentFragment = fragment;
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        if (!fragment.isAdded()) {
            transaction.add(R.id.container, fragment)
                    .commit();
        }
    }

}
