package com.bsoft.baselib.http.converter;


import java.text.ParseException;

import io.reactivex.annotations.NonNull;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/3/21.
 * Description:对网络请求返回结果进行解析，主要用于适配Rxjava的
 * PS: Not easy to write code, please indicate.
 */
public interface IConverter<T> {
    T convert(@NonNull String result) throws ParseException;

}
