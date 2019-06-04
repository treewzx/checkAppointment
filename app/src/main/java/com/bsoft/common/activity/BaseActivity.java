package com.bsoft.common.activity;

import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;

import com.bsoft.baselib.framework.mvc.BaseMvcActivity;
import com.bsoft.checkappointment.R;
import com.bsoft.common.utils.StatusBarUtil;
import com.bsoft.common.view.dialog.AlertDialog;
import com.bsoft.common.view.toobar.DefaultToolBar;
import com.wang.avi.AVLoadingIndicatorView;
import com.wang.avi.indicators.BallSpinFadeLoaderIndicator;


/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/5/28.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
public abstract class BaseActivity extends BaseMvcActivity {
    private AlertDialog.Builder mAlertDialogBuilder;
    private AVLoadingIndicatorView loadingView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public void initDefaultToolbar(String title) {
        StatusBarUtil.setDarkMode(this);
        new DefaultToolBar.Builder(this)
                .setStatusBarImmersed(true)
                .setTitle(title)
                .setToolBarColor(getResources().getColor(android.R.color.transparent))
                .build();
    }


    @Override
    public void showLoading() {
        //显示加载对话框
        mAlertDialogBuilder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_loading, null, false);
        loadingView = view.findViewById(R.id.loading_view);
        loadingView.setIndicator(new BallSpinFadeLoaderIndicator());
        mAlertDialogBuilder.setContentView(view)
                .setWidthAndHeight(getDimension(R.dimen.dp_140), getDimension(R.dimen.dp_140))
                .setCancelable(true)
                .show();
        loadingView.smoothToShow();
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
    }

    @Override
    public void showContent() {

    }

    @Override
    public void showError() {

    }

    public int fetchColor(@ColorRes int color) {
        return ContextCompat.getColor(this, color);
    }

    public int getDimension(@DimenRes int dimenId){
        return (int) getResources().getDimension(dimenId);
    }

}
