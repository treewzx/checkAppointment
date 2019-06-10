package com.bsoft.baselib.framework.mvc;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bsoft.baselib.framework.callback.LceCallback;
import com.bsoft.baselib.framework.callback.ViewCallback;
import com.bsoft.baselib.framework.delegate.FragmentDelegate;
import com.bsoft.baselib.framework.delegate.FragmentDelegateImpl;
import com.bsoft.baselib.framework.mvc.delegate.FragmentLifecycleDelegateImpl;
import com.bsoft.baselib.framework.mvc.lifecycle.FragmentLifecycleable;
import com.bsoft.checkappointment.R;
import com.trello.rxlifecycle2.android.FragmentEvent;

import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/4/25.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
public abstract class BaseMvcFragment extends Fragment implements ViewCallback, FragmentLifecycleable, LceCallback {
    private BehaviorSubject<FragmentEvent> lifecycleSubject;
    private FragmentDelegate mFragmentDelegate;
    protected Context mContext;

    private FragmentDelegate getFragmentDelegate() {
        if (mFragmentDelegate == null) {
            mFragmentDelegate = new FragmentLifecycleDelegateImpl(new FragmentDelegateImpl(this), this);
        }
        return mFragmentDelegate;
    }

    @Override
    public boolean useEventBus() {
        return false;
    }

    @Override
    public boolean useButterKnife() {
        return true;
    }


    @NonNull
    @Override
    public Subject<FragmentEvent> getLifecycleSubject() {
        if (lifecycleSubject == null) {
            lifecycleSubject = BehaviorSubject.create();
        }
        return lifecycleSubject;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getFragmentDelegate().onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentDelegate().onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getContentViewId(savedInstanceState), container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        super.onViewCreated(view, savedInstanceState);
        getFragmentDelegate().onCreateView(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        getFragmentDelegate().onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        getFragmentDelegate().onResume();
    }

    @Override
    public void onPause() {
        getFragmentDelegate().onPause();
        super.onPause();
    }

    @Override
    public void onStop() {
        getFragmentDelegate().onStop();
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        getFragmentDelegate().onDestroyView();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        getFragmentDelegate().onDestroy();
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        getFragmentDelegate().onDetach();
        super.onDetach();
    }

    public int getDimension(@DimenRes int dimenId) {
        return (int) getResources().getDimension(dimenId);
    }
}
