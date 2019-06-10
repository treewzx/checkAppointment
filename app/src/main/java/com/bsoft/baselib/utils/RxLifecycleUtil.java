package com.bsoft.baselib.utils;

import android.support.annotation.NonNull;

import com.bsoft.baselib.framework.mvc.lifecycle.ActivityLifecycleable;
import com.bsoft.baselib.framework.mvc.lifecycle.FragmentLifecycleable;
import com.bsoft.baselib.framework.mvc.lifecycle.Lifecycleable;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;


/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/3/3.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
public class RxLifecycleUtil {

    private RxLifecycleUtil() {
        throw new IllegalStateException("Can't instance the RxLifecycleUtils");
    }

    /*public static <T> AutoDisposeConverter<T> bindLifecycle(LifecycleOwner lifecycleOwner) {
        return AutoDispose.autoDisposable(
                AndroidLifecycleScopeProvider.from(lifecycleOwner)
        );
    }*/

   /* *//**
     * 绑定 Activity 的指定生命周期
     *
     * @param view
     * @param event
     * @param <T>
     * @return
     *//*
    public static <T> LifecycleTransformer<T> bindUntilEvent(@NonNull final IMvpView view,
                                                             final ActivityEvent event) {
        Preconditions.checkNotNull(view, "view == null");
        if (view instanceof ActivityLifecycleable) {
            return bindUntilEvent((ActivityLifecycleable) view, event);
        } else {
            throw new IllegalArgumentException("view isn't ActivityLifecycleable");
        }
    }

    *//**
     * 绑定 Fragment 的指定生命周期
     *
     * @param view
     * @param event
     * @param <T>
     * @return
     *//*
    public static <T> LifecycleTransformer<T> bindUntilEvent(@NonNull final IMvpView view,
                                                             final FragmentEvent event) {
        Preconditions.checkNotNull(view, "view == null");
        if (view instanceof FragmentLifecycleable) {
            return bindUntilEvent((FragmentLifecycleable) view, event);
        } else {
            throw new IllegalArgumentException("view isn't FragmentLifecycleable");
        }
    }

    public static <T, R> LifecycleTransformer<T> bindUntilEvent(@NonNull final Lifecycleable<R> lifecycleable,
                                                                final R event) {
        Preconditions.checkNotNull(lifecycleable, "lifecycleable == null");
        return RxLifecycle.bindUntilEvent(lifecycleable.getLifecycleSubject(), event);
    }



    *//**
     * 绑定 Activity/Fragment 的生命周期
     *

     * @param <T>
     * @return
     *//*
    public static <T> LifecycleTransformer<T> bindToLifecycle(@NonNull IMvpView view) {
        Preconditions.checkNotNull(view, "view == null");
        if (view instanceof Lifecycleable) {
            return bindToLifecycle((Lifecycleable) view);
        } else {
            throw new IllegalArgumentException("view isn't Lifecycleable");
        }
    }

   */
    public static <T, R> LifecycleTransformer<T> bindUntilEvent(@NonNull final Lifecycleable<R> lifecycleable,
                                                                final R event) {
        Preconditions.checkNotNull(lifecycleable, "lifecycleable == null");
        return RxLifecycle.bindUntilEvent(lifecycleable.getLifecycleSubject(), event);
    }

    public static <T> LifecycleTransformer<T> bindToLifecycle(@NonNull Lifecycleable lifecycleable) {
        Preconditions.checkNotNull(lifecycleable, "lifecycleable == null");
        if (lifecycleable instanceof ActivityLifecycleable) {
            return RxLifecycleAndroid.bindActivity(((ActivityLifecycleable) lifecycleable).getLifecycleSubject());
        } else if (lifecycleable instanceof FragmentLifecycleable) {
            return RxLifecycleAndroid.bindFragment(((FragmentLifecycleable) lifecycleable).getLifecycleSubject());
        } else {
            throw new IllegalArgumentException("Lifecycleable not match");
        }
    }
}
