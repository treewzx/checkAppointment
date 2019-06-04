package com.bsoft.baselib.framework.mvc;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.bsoft.baselib.framework.callback.LceCallback;
import com.bsoft.baselib.framework.callback.ViewCallback;
import com.bsoft.baselib.framework.delegate.ActivityDelegate;
import com.bsoft.baselib.framework.delegate.ActivityDelegateImpl;
import com.bsoft.baselib.framework.mvc.delegate.ActivityLifecycleDelegateImpl;
import com.bsoft.baselib.framework.mvc.lifecycle.ActivityLifecycleable;
import com.trello.rxlifecycle2.android.ActivityEvent;

import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/4/24.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
public abstract class BaseMvcActivity extends AppCompatActivity implements ViewCallback, ActivityLifecycleable,LceCallback {
    private ActivityDelegate mActivityDelegate;
    private BehaviorSubject<ActivityEvent> lifecycleSubject;

    private ActivityDelegate getActivityDelegate() {
        if (mActivityDelegate == null) {
            mActivityDelegate = new ActivityLifecycleDelegateImpl(new ActivityDelegateImpl(this), this);
        }
        return mActivityDelegate;
    }


    /**
     * 默认使用ButterKnife
     *
     * @return
     */
    @Override
    public boolean useButterKnife() {
        return true;
    }

    /**
     * 默认不适用EventBus
     *
     * @return
     */
    @Override
    public boolean useEventBus() {
        return false;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId(savedInstanceState));
        getActivityDelegate().onCreate(savedInstanceState);

    }

    @Override
    protected void onStart() {
        super.onStart();
        getActivityDelegate().onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getActivityDelegate().onResume();
    }

    @Override
    protected void onPause() {
        getActivityDelegate().onPause();
        super.onPause();

    }

    @Override
    protected void onStop() {
        getActivityDelegate().onStop();
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        getActivityDelegate().onDestroy();
        super.onDestroy();

    }

    @NonNull
    @Override
    public BehaviorSubject<ActivityEvent> getLifecycleSubject() {
        if (lifecycleSubject == null) {
            lifecycleSubject = BehaviorSubject.create();
        }
        return lifecycleSubject;
    }
}
