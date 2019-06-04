package com.bsoft.baselib.framework.delegate;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.bsoft.baselib.framework.callback.ViewCallback;
import com.bsoft.baselib.framework.callback.ProxyViewCallback;
import com.bsoft.baselib.framework.helper.EventBusManager;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/3/3.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
public class FragmentDelegateImpl implements FragmentDelegate {

    private ViewCallback mViewCallback;
    private Fragment mFragment;
    private Unbinder mUnBinder;

    public FragmentDelegateImpl(ViewCallback viewCallback) {
        mViewCallback = new ProxyViewCallback(viewCallback);
        mFragment = (Fragment) viewCallback;

    }

    @Override
    public void onAttach(@NonNull Context context) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        //注册EventBus
        if (mViewCallback.useEventBus()) {
            EventBusManager.getInstance().register(mFragment);
        }
    }

    @Override
    public void onCreateView(@Nullable View view, @Nullable Bundle savedInstanceState) {
        if (mViewCallback.useButterKnife()) {
            mUnBinder = ButterKnife.bind(mFragment, view);
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
    public void onDestroyView() {
        if (mUnBinder != null && mViewCallback.useButterKnife()) {
            mUnBinder.unbind();
        }
    }

    @Override
    public void onDestroy() {
        if (mViewCallback.useEventBus()) {
            EventBusManager.getInstance().unregister(mFragment);
        }
    }

    @Override
    public void onDetach() {

    }

}
