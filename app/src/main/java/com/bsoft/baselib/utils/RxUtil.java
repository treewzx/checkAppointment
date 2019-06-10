package com.bsoft.baselib.utils;

import com.bsoft.baselib.framework.callback.LceCallback;
import com.bsoft.baselib.framework.mvc.lifecycle.Lifecycleable;


import io.reactivex.Observable;
import io.reactivex.ObservableConverter;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/3/5.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
public class RxUtil {

    /**
     * 切换线程
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> applySchedulers() {
        return new ObservableTransformer<T, T>() {
            @Override
            public Observable<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 切换现成并绑定页面的生命周期
     *
     * @param lifecycleable
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> applyLifecycleSchedulers(final Lifecycleable lifecycleable) {
        return new ObservableTransformer<T, T>() {
            @Override
            public Observable<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(RxLifecycleUtil.bindToLifecycle(lifecycleable));
            }
        };
    }

    /**
     * 切换现成并绑定页面的生命周期
     *
     * @param lifecycleable
     * @param <T>
     * @return
     */
    public static <R, T> ObservableTransformer<T, T> applyLifecycleSchedulers(final Lifecycleable<R> lifecycleable, final R event) {
        return new ObservableTransformer<T, T>() {
            @Override
            public Observable<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(RxLifecycleUtil.bindUntilEvent(lifecycleable, event));
            }
        };
    }

    /**
     * 切换线程、订阅绑定页面的生命周期并且订阅时显示加载中，完成后隐藏加载中
     *
     * @param lifecycleable
     * @param lceCallback
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> applyLifecycleLCESchedulers(final Lifecycleable lifecycleable, final LceCallback lceCallback) {
        return new ObservableTransformer<T, T>() {
            @Override
            public Observable<T> apply(Observable<T> upstream) {

                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(RxLifecycleUtil.<T>bindToLifecycle(lifecycleable))
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                lceCallback.showLoading();
                            }
                        }).doFinally(new Action() {
                            @Override
                            public void run() throws Exception {
                                lceCallback.hideLoading();
                            }
                        });
            }
        };
    }

   /* public static <T> ObservableTransformer<T,T> applySchedulers(final  view, final ActivityEvent event){
        return  new ObservableTransformer<T, T>() {
            @Override
            public Observable<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(RxLifecycleUtil.<T>bindUntilEvent(view,event));
            }
        };
    }

    public static <T> ObservableTransformer<T,T> applySchedulers(final IMvpView view, final FragmentEvent event){
        return  new ObservableTransformer<T, T>() {
            @Override
            public Observable<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(RxLifecycleUtil.<T>bindUntilEvent(view,event));
            }
        };
    }*/


    /**
     * 使用uber的AutoDisposed框架
     * @param lifecycleOwner
     * @param upstream
     * @param <T>
     * @return
     */
    /*public static <T> ObservableSubscribeProxy<T> applySchedulers(final LifecycleOwner lifecycleOwner, Observable<T> upstream) {
        return upstream
                .compose(applySchedulers())
                .as((ObservableConverter<Object, ? extends ObservableSubscribeProxy<T>>) RxLifecycleUtil.<T>bindLifecycle(lifecycleOwner));
    }*/
}
