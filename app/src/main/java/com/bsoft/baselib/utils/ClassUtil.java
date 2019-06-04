package com.bsoft.baselib.utils;

import com.alibaba.fastjson.TypeReference;
import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/3/11.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
public class ClassUtil {


    /**
     * 获取对象上指定位置泛型的Type
     *
     * @param object
     * @param index
     * @return
     */
    public static final Type getSuperclassTypeParameter(Object object, int index) {
        Type superclass = object.getClass().getGenericSuperclass();
        if (!(superclass instanceof ParameterizedType)) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameter = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameter.getActualTypeArguments()[index]);
    }

    /**
     * 获取对象上指定位置泛型的类型
     *
     * @param object
     * @param index
     * @return
     */
    public static Class<?> analysisClazzInfo(Object object, int index) {
        Type superclass = object.getClass().getGenericSuperclass();
        ParameterizedType genType = (ParameterizedType) superclass;
        Type[] params = genType.getActualTypeArguments();
        return (Class<?>) (params[index]);
    }

}
