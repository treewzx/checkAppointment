package com.bsoft.baselib.http.httpcallback;

import java.util.Map;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/3/11.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
public interface IHttpCallback {

    /** 请求网络开始前*/
    void onPreRequest(Map<String, String> headers, Map<String, String> params);

    /** 对返回数据进行操作的回调 */
    void onSuccess(String result);


    /** 请求失败，响应错误，数据解析错误等，都会回调该方法*/
    void onFailure(Throwable throwable);

    /** 请求网络结束 */
    void onFinish();


}
