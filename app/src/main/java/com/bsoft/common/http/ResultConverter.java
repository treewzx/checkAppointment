package com.bsoft.common.http;

import com.alibaba.fastjson.JSON;
import com.bsoft.baselib.http.converter.IConverter;
import com.bsoft.baselib.utils.ClassUtil;

import java.lang.reflect.Type;
import java.text.ParseException;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/5/31.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
public class ResultConverter<T> implements IConverter<T> {

    @Override
    public T convert(String result) throws ParseException {
        //第一步将基本数据转为model
       HttpBaseResultVo resultVo =  JSON.parseObject(result,HttpBaseResultVo.class);
       if(resultVo.code ==1 || resultVo.code==200){
           //成功则解析对象
           Type type = ClassUtil.getSuperclassTypeParameter(this, 0);
           T t = (T) JSON.parseObject(resultVo.data,type);
           return t;
       }else {
           throw new ParseException(resultVo.message,0);
       }
    }
}
