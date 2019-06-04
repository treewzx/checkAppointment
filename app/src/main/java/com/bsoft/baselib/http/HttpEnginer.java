package com.bsoft.baselib.http;

import android.content.Context;

import com.bsoft.baselib.http.converter.IConverter;
import com.bsoft.baselib.http.entity.ProgressInfo;
import com.bsoft.baselib.http.httpcallback.IHttpCallback;
import com.bsoft.baselib.http.request.listener.OnLoadProgressListener;
import com.bsoft.baselib.http.request.retrofit.RetrofitRequest;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;


/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/3/11.
 * Description:网络引擎
 * PS: Not easy to write code, please indicate.
 */
public class HttpEnginer {
    private static HttpEnginerConfig mHttpEnginerConfig;
    private static Map<String, String> mParams;
    private static Map<String, String> mHeaders;
    private static boolean mIsInterceptRequestParams = true;
    private String mUrl;
    private static BiFunction<Map<String, String>, Map<String, String>, RequestParamMap> mOnInterceptFunction;
    private OnLoadProgressListener mOnLoadProgressListener; //上传/下载文件的进度监听

    private HttpEnginer() {
        mParams = new HashMap<>();
        mHeaders = new HashMap<>();
    }

    public static HttpEnginer newInstance() {
        return new HttpEnginer();
    }


    public static void init(String baseUrl) {
        mHttpEnginerConfig = new HttpEnginerConfig.Builder()
                .httpRequest(new RetrofitRequest())
                .baseUrl(baseUrl)
                .build();
    }
    //针对httpEnginerConfig设置了BaseUrl的情况
    public static void init(HttpEnginerConfig httpEnginerConfig) {
        mHttpEnginerConfig = httpEnginerConfig;
    }

    public static void init(HttpEnginerConfig httpEnginerConfig, String baseUrl) {
        mHttpEnginerConfig = httpEnginerConfig.newBuilder()
                .baseUrl(baseUrl)
                .build();
    }

    public HttpEnginer addUrl(String url) {
        mUrl = url;
        return this;
    }

    public HttpEnginer addHeader(String key, String value) {
        mHeaders.put(key, value);
        return this;
    }

    public HttpEnginer addHeaders(Map<String, String> headers) {
        mHeaders.putAll(headers);
        return this;
    }

    public HttpEnginer addParam(String key, String value) {
        mParams.put(key, value);
        return this;
    }
    public HttpEnginer addParam(String key, int value) {
        mParams.put(key, String.valueOf(value));
        return this;
    }

    public HttpEnginer addParams(Map<String, String> params) {
        mParams.putAll(params);
        return this;
    }


    public HttpEnginer setOnLoadProgressListener(OnLoadProgressListener listener) {
        mOnLoadProgressListener = listener;
        return this;
    }


    /**
     * 是否拦截请求头和请求参数处理。默认是true
     *
     * @param isInterceptRequestParams
     * @return
     */
    public HttpEnginer setInterceptRequestParams(boolean isInterceptRequestParams) {
        mIsInterceptRequestParams = isInterceptRequestParams;
        return this;
    }

    public static HttpEnginerConfig getHttpEnginerConfig() {
        if(mHttpEnginerConfig == null){
            mHttpEnginerConfig = new HttpEnginerConfig.Builder().build();
        }
        return mHttpEnginerConfig;
    }


    /*************************以下是对请求头和请求参数做一层拦截处理*****************************/
    public static void setOnInterceptFunction(BiFunction<Map<String, String>, Map<String, String>, RequestParamMap> function) {
        mOnInterceptFunction = function;
    }

    private RequestParamMap onPreRequest(Map<String, String> headers, Map<String, String> params) {
        BiFunction<Map<String, String>, Map<String, String>, RequestParamMap> f = mOnInterceptFunction;
        RequestParamMap paramMap = new RequestParamMap(headers, params);
        if (f == null || !mIsInterceptRequestParams){
            mIsInterceptRequestParams = true;
            return paramMap;
        }
        try {
            return f.apply(headers, params);
        } catch (Exception e) {
            return paramMap;
        }
    }

    /****************************以下是网络请求*********************************/

    /********************************Get请求 start *************************************/
    public void get(IHttpCallback callback) {
        callback.onPreRequest(mHeaders, mParams);
        RequestParamMap paramMap = onPreRequest(mHeaders, mParams);
        mHttpEnginerConfig.getHttpRequest().get(mHttpEnginerConfig, mUrl, paramMap.getHeaders(), paramMap.getParams(), callback);
    }


    public Observable<String> get() {
        RequestParamMap paramMap = onPreRequest(mHeaders, mParams);
        return mHttpEnginerConfig.getHttpRequest().get(mHttpEnginerConfig, mUrl, paramMap.getHeaders(), paramMap.getParams());
    }

    public <T> Observable<T> get(IConverter<T> converter) {
        RequestParamMap paramMap = onPreRequest(mHeaders, mParams);
        return mHttpEnginerConfig.getHttpRequest().get(mHttpEnginerConfig, mUrl, paramMap.getHeaders(), paramMap.getParams(), converter);
    }


    /***************************post请求 start ******************************/
    public void post(IHttpCallback callback) {
        callback.onPreRequest(mHeaders, mParams);
        RequestParamMap paramMap = onPreRequest(mHeaders, mParams);
        mHttpEnginerConfig.getHttpRequest().post(mHttpEnginerConfig, mUrl, paramMap.getHeaders(), paramMap.getParams(), callback);
    }

    public Observable<String> post() {
        RequestParamMap paramMap = onPreRequest(mHeaders, mParams);
        return mHttpEnginerConfig.getHttpRequest().post(mHttpEnginerConfig, mUrl, paramMap.getHeaders(), paramMap.getParams());
    }

    public <T> Observable<T> post(IConverter<T> converter) {
        RequestParamMap paramMap = onPreRequest(mHeaders, mParams);
        return mHttpEnginerConfig.getHttpRequest().post(mHttpEnginerConfig, mUrl, paramMap.getHeaders(), paramMap.getParams(), converter);
    }


    /*******************************下载文件 start ******************************/

    public void downloadAsync(Context context, String url, String saveRootPath) {
        downloadAsync(context, url, saveRootPath, null);
    }

    /**
     * 异步下载，直接在主线程调用即可
     *
     * @param context
     * @param url
     * @param saveRootPath 保存文件的根目录
     * @param fileName     保存文件的文件名
     */
    public void downloadAsync(Context context, String url, String saveRootPath, String fileName) {
        mHttpEnginerConfig.getHttpRequest().downloadAsync(context, mHttpEnginerConfig, url, saveRootPath, fileName, mOnLoadProgressListener);
    }


    public Observable<ProgressInfo> download(Context context, String url, String saveRootPath) {
        return download(context, url, saveRootPath, null);
    }

    /**
     * 同步下载，兼容Rxjava
     *
     * @param context
     * @param url
     * @param saveRootPath
     * @param fileName
     * @return
     */
    public Observable<ProgressInfo> download(Context context, String url, String saveRootPath, String fileName) {
        return mHttpEnginerConfig.getHttpRequest().download(context, mHttpEnginerConfig, url, saveRootPath, fileName);
    }


    /**********************************上传文件 start ****************************/

    public void uploadAsync(File file, IHttpCallback callback) {
        uploadAsync(callback, file);
    }

    public void uploadAsync(String filePath, IHttpCallback callback) {
        uploadAsync(callback, filePath);
    }

    public void uploadAsync(IHttpCallback callback, String... filePaths) {
        File[] files = new File[filePaths.length];
        for (int i = 0; i < filePaths.length; i++) {
            files[i] = new File(filePaths[i]);
        }
        uploadAsync(callback, files);
    }

    /**
     * 异步上传文件，直接在主线程中调用即可
     *
     * @param callback
     * @param files
     */
    public void uploadAsync(IHttpCallback callback, File... files) {
        RequestParamMap paramMap = onPreRequest(mHeaders, mParams);
        mHttpEnginerConfig.getHttpRequest().uploadAsync(mHttpEnginerConfig, mUrl, paramMap.getHeaders(), paramMap.getParams(), callback, mOnLoadProgressListener, files);
    }

    /**
     * 同步上传文件，兼容Rxjava
     *
     * @param files
     * @return
     */
    public Observable<ProgressInfo> upload(File... files) {
        RequestParamMap paramMap = onPreRequest(mHeaders, mParams);
        return mHttpEnginerConfig.getHttpRequest().upload(mHttpEnginerConfig, mUrl, paramMap.getHeaders(), paramMap.getParams(), files);
    }


}
