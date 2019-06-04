package com.bsoft.baselib.framework.mvc.delegate;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bsoft.baselib.framework.delegate.ActivityDelegate;
import com.bsoft.baselib.framework.mvc.lifecycle.ActivityLifecycleable;
import com.trello.rxlifecycle2.android.ActivityEvent;

import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/4/25.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
public class ActivityLifecycleDelegateImpl implements ActivityDelegate {
    private ActivityDelegate mActivityDelegate;
    private ActivityLifecycleable mActivityLifecycleable;

    public ActivityLifecycleDelegateImpl(ActivityDelegate activityDelegate, ActivityLifecycleable activityLifecycleable) {
        mActivityDelegate = activityDelegate;
        mActivityLifecycleable = activityLifecycleable;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mActivityDelegate.onCreate(savedInstanceState);
        getLifecycleSubject().onNext(ActivityEvent.CREATE);
    }

    @Override
    public void onStart() {
        mActivityDelegate.onStart();
        getLifecycleSubject().onNext(ActivityEvent.START);
    }

    @Override
    public void onResume() {
        mActivityDelegate.onResume();
        getLifecycleSubject().onNext(ActivityEvent.RESUME);
    }

    @Override
    public void onPause() {
        mActivityDelegate.onPause();
        getLifecycleSubject().onNext(ActivityEvent.PAUSE);
    }

    @Override
    public void onStop() {
        mActivityDelegate.onStop();
        getLifecycleSubject().onNext(ActivityEvent.STOP);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        mActivityDelegate.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        mActivityDelegate.onDestroy();
        getLifecycleSubject().onNext(ActivityEvent.DESTROY);
    }


    public Subject getLifecycleSubject() {
        return mActivityLifecycleable.getLifecycleSubject();
    }
}
