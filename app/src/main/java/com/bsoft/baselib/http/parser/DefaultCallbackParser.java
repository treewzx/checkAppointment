package com.bsoft.baselib.http.parser;


/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/4/12.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
public class DefaultCallbackParser implements ICallbackParser {
    @Override
    public String convert(String result, Class clazz)  {
        return result;
    }
}
