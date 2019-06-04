package com.bsoft.baselib.http.parser;

import com.alibaba.fastjson.JSON;

import java.text.ParseException;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/4/12.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
public class CallbackGsonParser implements ICallbackParser {
    @Override
    public <E> E convert(String result, Class<E> clazz)  {
        return JSON.parseObject(result, clazz);
    }
}
