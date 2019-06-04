package com.bsoft.baselib.http.parser;

import java.text.ParseException;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/4/12.
 * Description:对网络异步请求返回的结果进行解析,可在网络引擎的配置中进行设置，但是起作用的前提是用户使用的请求中有用到callback回调
 * PS: Not easy to write code, please indicate.
 */
public interface ICallbackParser {

    <E> E convert(String result, Class<E> clazz)throws ParseException;
}
