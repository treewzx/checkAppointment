package com.bsoft.baselib.framework.mvc.lifecycle;

import android.support.annotation.NonNull;

import io.reactivex.subjects.Subject;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/4/25.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
public interface Lifecycleable<E> {
    @NonNull
    Subject<E> getLifecycleSubject();
}

