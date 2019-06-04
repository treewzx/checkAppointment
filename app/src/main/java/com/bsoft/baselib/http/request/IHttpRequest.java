package com.bsoft.baselib.http.request;

import android.content.Context;
import android.support.annotation.Nullable;

import com.bsoft.baselib.http.HttpEnginerConfig;
import com.bsoft.baselib.http.converter.IConverter;
import com.bsoft.baselib.http.entity.ProgressInfo;
import com.bsoft.baselib.http.httpcallback.IHttpCallback;
import com.bsoft.baselib.http.request.listener.OnLoadProgressListener;

import java.io.File;
import java.util.Map;

import io.reactivex.Observable;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/3/11.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
public interface IHttpRequest {

    /**********************************Get请求*****************************************/

    void get(HttpEnginerConfig config, String url, Map<String, String> headers, Map<String, String> params, IHttpCallback callback);

    Observable<String> get(final HttpEnginerConfig config, final String url, final Map<String, String> headers, final Map<String, String> params);

    <T> Observable<T> get(HttpEnginerConfig config, String url, Map<String, String> headers, Map<String, String> params, IConverter<T> converter);


    /***********************************Post请求***************************************/

    void post(HttpEnginerConfig config, String url, Map<String, String> headers, Map<String, String> params, IHttpCallback callback);

    Observable<String> post(final HttpEnginerConfig config, final String url, final Map<String, String> headers, final Map<String, String> params);

    <T> Observable<T> post(HttpEnginerConfig config, String url, Map<String, String> headers, Map<String, String> params, IConverter<T> converter);


    /*****************************************下载文件*************************************/

    void downloadAsync(Context context, HttpEnginerConfig config, String url, String saveRootPath, @Nullable String fileName, @Nullable OnLoadProgressListener listener);

    Observable<ProgressInfo> download(Context context, HttpEnginerConfig config, String url, String saveRootPath, @Nullable String fileName);


    /********************************************上传文件*************************************/

    void uploadAsync(HttpEnginerConfig config, String url, Map<String, String> headers, Map<String, String> params, IHttpCallback callback, @Nullable OnLoadProgressListener listener, File... files);

    Observable<ProgressInfo> upload(HttpEnginerConfig config, String url, Map<String, String> headers, Map<String, String> params, File... files);


}
