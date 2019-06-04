package com.bsoft.baselib.framework.mvc.delegate;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.bsoft.baselib.framework.delegate.FragmentDelegate;
import com.bsoft.baselib.framework.mvc.lifecycle.FragmentLifecycleable;
import com.trello.rxlifecycle2.android.FragmentEvent;

import io.reactivex.subjects.Subject;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/4/25.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
public class FragmentLifecycleDelegateImpl implements FragmentDelegate {
    private FragmentDelegate mFragmentDelegate;
    private FragmentLifecycleable mFragmentLifecycleable;

    public FragmentLifecycleDelegateImpl(FragmentDelegate fragmentDelegate, FragmentLifecycleable fragmentLifecycleable) {
        this.mFragmentDelegate = fragmentDelegate;
        this.mFragmentLifecycleable = fragmentLifecycleable;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        mFragmentDelegate.onAttach(context);
        getLifecycleSubject().onNext(FragmentEvent.ATTACH);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mFragmentDelegate.onCreate(savedInstanceState);
        getLifecycleSubject().onNext(FragmentEvent.CREATE);
    }

    @Override
    public void onCreateView(@Nullable View view, @Nullable Bundle savedInstanceState) {
        mFragmentDelegate.onCreateView(view, savedInstanceState);
        getLifecycleSubject().onNext(FragmentEvent.CREATE_VIEW);
    }

    @Override
    public void onStart() {
        mFragmentDelegate.onStart();
        getLifecycleSubject().onNext(FragmentEvent.START);

    }

    @Override
    public void onResume() {
        mFragmentDelegate.onResume();
        getLifecycleSubject().onNext(FragmentEvent.RESUME);
    }

    @Override
    public void onPause() {
        getLifecycleSubject().onNext(FragmentEvent.PAUSE);
        mFragmentDelegate.onPause();

    }

    @Override
    public void onStop() {
        getLifecycleSubject().onNext(FragmentEvent.STOP);
        mFragmentDelegate.onStop();

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        mFragmentDelegate.onSaveInstanceState(outState);

    }

    @Override
    public void onDestroyView() {
        getLifecycleSubject().onNext(FragmentEvent.DESTROY_VIEW);
        mFragmentDelegate.onDestroyView();
    }

    @Override
    public void onDestroy() {
        getLifecycleSubject().onNext(FragmentEvent.DESTROY);
        mFragmentDelegate.onDestroy();
    }

    @Override
    public void onDetach() {
        getLifecycleSubject().onNext(FragmentEvent.DETACH);
        mFragmentDelegate.onDetach();

    }

    public Subject getLifecycleSubject() {
        return mFragmentLifecycleable.getLifecycleSubject();
    }
}
