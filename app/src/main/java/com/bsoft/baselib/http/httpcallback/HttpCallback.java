package com.bsoft.baselib.http.httpcallback;

import com.bsoft.baselib.http.HttpEnginer;
import com.bsoft.baselib.http.exception.ApiException;
import com.bsoft.baselib.http.exception.ExceptionEnginer;
import com.bsoft.baselib.http.parser.ICallbackParser;
import com.bsoft.baselib.utils.ClassUtil;

import java.text.ParseException;
import java.util.Map;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/4/12.
 * Description:异步请求的网络回调
 * PS: Not easy to write code, please indicate.
 */
public abstract class HttpCallback<T> implements IHttpCallback {

    private ICallbackParser mParser;

    public HttpCallback() {
    }

    /**
     * 给回调结果设置指定的解析器（不常用，特殊要求时候使用），一般使用引擎配置（HttpEnginerConfig）中统一配置的解析器即可
     *
     * @param parser
     */
    public HttpCallback(ICallbackParser parser) {
        mParser = parser;
    }

    @Override
    public void onPreRequest(Map<String, String> headers, Map<String, String> params) {

    }

    @Override
    public void onSuccess(String result) {

        try {
            Class<T> clazz = (Class<T>) ClassUtil.analysisClazzInfo(this, 0);
            mParser = HttpEnginer.getHttpEnginerConfig().getCallbackParser();
            onSuccess(mParser.convert(result, clazz));
        } catch (Exception e) {
            onFailure(new ParseException("解析错误", 0));
        }
    }

    @Override
    public void onFailure(Throwable throwable) {
        onFailed(ExceptionEnginer.handleException(throwable));
    }

    @Override
    public void onFinish() {

    }

    protected abstract void onSuccess(T resultT);

    public abstract void onFailed(ApiException e);
}
