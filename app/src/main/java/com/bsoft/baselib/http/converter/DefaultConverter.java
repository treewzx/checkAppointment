package com.bsoft.baselib.http.converter;

import java.text.ParseException;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/4/12.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
public class DefaultConverter implements IConverter<String> {
    @Override
    public String convert(String result) throws ParseException {
        return result;
    }
}
