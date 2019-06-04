package com.bsoft.baselib.framework.callback;

import android.os.Bundle;
import android.support.annotation.Nullable;


/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/2/28.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
public class ProxyViewCallback implements ViewCallback {
    private ViewCallback mViewCallback;

    public ProxyViewCallback(ViewCallback viewCallback) {
        this.mViewCallback = viewCallback;
    }

    @Override
    public int getContentViewId(@Nullable Bundle savedInstanceState) {
        return mViewCallback.getContentViewId(savedInstanceState);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mViewCallback.initData(savedInstanceState);
    }

    @Override
    public boolean useEventBus() {
        return mViewCallback.useEventBus();
    }

    @Override
    public boolean useButterKnife() {
        return mViewCallback.useButterKnife();
    }

}
