package com.bsoft.checkappointment.operationsearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.barnettwong.dragfloatactionbuttonlibrary.view.DragFloatActionButton;
import com.bsoft.baselib.http.HttpEnginer;
import com.bsoft.baselib.http.exception.ApiException;
import com.bsoft.baselib.utils.RxUtil;
import com.bsoft.checkappointment.MyApplication;
import com.bsoft.checkappointment.R;
import com.bsoft.checkappointment.model.OperationVo;
import com.bsoft.checkappointment.model.PatientAppointmentVo;
import com.bsoft.checkappointment.operationsearch.fragment.OperationCurrentFragment;
import com.bsoft.checkappointment.operationsearch.fragment.OperationHistoryFragment;
import com.bsoft.common.activity.BaseActivity;
import com.bsoft.common.adapter.CommonAdapter;
import com.bsoft.common.adapter.ViewHolder;
import com.bsoft.common.http.BaseObserver;
import com.bsoft.common.http.ResultConverter;
import com.bsoft.common.utils.ToastUtil;
import com.bsoft.common.view.swapview.LoadViewHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/6/10.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
public class OperationSearchActivity extends BaseActivity {
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
