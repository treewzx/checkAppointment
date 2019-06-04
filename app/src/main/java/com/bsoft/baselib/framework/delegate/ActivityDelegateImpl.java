package com.bsoft.baselib.framework.delegate;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bsoft.baselib.framework.callback.ViewCallback;
import com.bsoft.baselib.framework.callback.ProxyViewCallback;
import com.bsoft.baselib.framework.helper.EventBusManager;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/2/28.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
public class ActivityDelegateImpl implements ActivityDelegate {
    private ViewCallback mViewCallback;
    private Unbinder mUnBinder;
    private Activity mActivity;

    public ActivityDelegateImpl(ViewCallback viewCallback) {
        this.mViewCallback = new ProxyViewCallback(viewCallback);
        mActivity = (Activity) viewCallback;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if(mViewCallback.useButterKnife()){
            mUnBinder = ButterKnife.bind(mActivity);
        }
        if (mViewCallback.useEventBus()) {
            EventBusManager.getInstance().register(mActivity);
        }
        mViewCallback.initData(savedInstanceState);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

    }


    @Override
    public void onDestroy() {
        if (mViewCallback.useButterKnife() && mUnBinder != null) {
            mUnBinder.unbind();
        }
        mUnBinder = null;
        if (mViewCallback.useEventBus()) {
            EventBusManager.getInstance().unregister(mActivity);
        }

    }
}
