package com.bsoft.common.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.bsoft.baselib.framework.mvc.BaseMvcLazyLoadFragment;
import com.bsoft.checkappointment.R;
import com.bsoft.common.view.dialog.AlertDialog;
import com.bsoft.common.view.swapview.LoadViewHelper;
import com.wang.avi.AVLoadingIndicatorView;
import com.wang.avi.indicators.BallSpinFadeLoaderIndicator;

import java.util.zip.Inflater;


/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/5/28.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
public abstract class BaseLazyLoadFragment extends BaseMvcLazyLoadFragment {
    private SwipeRefreshLayout mRefreshLayout;
    protected RecyclerView recyclerView;
    protected LoadViewHelper mLoadViewHelper;
    protected SwipeRefreshLayout.OnRefreshListener mOnRefreshListener;
    private AlertDialog.Builder mAlertDialogBuilder;
    private AVLoadingIndicatorView loadingView;
    private View dialogView;

    @Override
    public void showLoading() {
        //显示加载对话框
        dialogView = LayoutInflater.from(mContext).inflate(R.layout.dialog_loading, null, false);
        mAlertDialogBuilder = new AlertDialog.Builder(getActivity());
        mAlertDialogBuilder.setContentView(dialogView)
                .setWidthAndHeight(getDimension(R.dimen.dp_140), getDimension(R.dimen.dp_140))
                .setCancelable(true)
                .show();
        loadingView = dialogView.findViewById(R.id.loading_view);
        loadingView.setIndicator(new BallSpinFadeLoaderIndicator());
        loadingView.smoothToShow();
    }

    @Override
    public void showError() {
        if (mLoadViewHelper != null) {
            mLoadViewHelper.showError(v -> loadData());
        }
    }

    @Override
    public void showContent() {

    }

    @Override
    public void hideLoading() {
        //隐藏加载对话框
        if (mAlertDialogBuilder != null) {
            mAlertDialogBuilder.dismiss();
        }
        if (loadingView != null) {
            loadingView.smoothToHide();
        }
        //隐藏刷新图标
        if (mLoadViewHelper != null) {
            mLoadViewHelper.stopRefreshing();
        }
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mRefreshLayout = getView().findViewById(R.id.swipeRefreshLayout);
        recyclerView = getView().findViewById(R.id.recyclerview);
        if (mRefreshLayout != null) {
            mRefreshLayout.setColorSchemeResources(R.color.main);
            mLoadViewHelper = new LoadViewHelper(mRefreshLayout);
            mLoadViewHelper.bindRefreshLayout(mRefreshLayout);
            mOnRefreshListener = () -> loadData();
            mRefreshLayout.setOnRefreshListener(mOnRefreshListener);
        }
    }


}
