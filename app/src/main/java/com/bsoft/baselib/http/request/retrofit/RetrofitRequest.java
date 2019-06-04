package com.bsoft.baselib.http.request.retrofit;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.bsoft.baselib.http.HttpEnginerConfig;
import com.bsoft.baselib.http.converter.IConverter;
import com.bsoft.baselib.http.entity.ProgressInfo;
import com.bsoft.baselib.http.exception.HttpException;
import com.bsoft.baselib.http.exception.IOStreamReadException;
import com.bsoft.baselib.http.httpcallback.IHttpCallback;
import com.bsoft.baselib.http.request.IHttpRequest;
import com.bsoft.baselib.http.request.listener.OnLoadProgressListener;
import com.bsoft.baselib.http.request.retrofit.download.DownLoadManager;
import com.bsoft.baselib.http.request.retrofit.upload.ExMultipartBody;
import com.bsoft.baselib.utils.FileUtil;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;


import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/3/11.
 * Description:Retrofit执行真正的网络请求
 * PS: Not easy to write code, please indicate.
 */
public class RetrofitRequest implements IHttpRequest {
    private Call<ResponseBody> call;


    /***********************Get请求**************************/
    @Override
    public void get(HttpEnginerConfig config, String url, Map<String, String> headers, Map<String, String> params, final IHttpCallback callback) {
        call = RetrofitClient.getServiceApi(config).get(headers, url, params);
        enqueue(call, callback);
    }

    @Override
    public Observable<String> get(final HttpEnginerConfig config, final String url, final Map<String, String> headers, final Map<String, String> params) {
        return Observable.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                call = RetrofitClient.getServiceApi(config).get(headers, url, params);
                return excute(call);
            }
        });
    }

    @Override
    public <T> Observable<T> get(final HttpEnginerConfig config, final String url, final Map<String, String> headers, final Map<String, String> params, final IConverter<T> converter) {
        return get(config, url, headers, params).map(new Function<String, T>() {
            @Override
            public T apply(String result) throws ParseException {
                if (!TextUtils.isEmpty(result)) {
                    try {
                        T t = converter.convert(result);
                        return t;
                    } catch (Exception e) {
                        throw new ParseException("解析错误", 0);
                    }
                }
                return null;
            }
        });

    }


    /**************************************Post请求************************************/

    @Override
    public void post(HttpEnginerConfig config, String url, Map<String, String> headers, Map<String, String> params, final IHttpCallback callback) {
        if(config.getMediaType()==HttpEnginerConfig.MediaType.FORM){
            call = RetrofitClient.getServiceApi(config).post(headers, url, params);
        }else {
            String paramsJsonStr = JSON.toJSONString(params);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),paramsJsonStr);
            call = RetrofitClient.getServiceApi(config).post(headers, url, requestBody);
        }
        enqueue(call, callback);
    }

    @Override
    public Observable<String> post(final HttpEnginerConfig config, final String url, final Map<String, String> headers, final Map<String, String> params) {
        return Observable.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                if(config.getMediaType()==HttpEnginerConfig.MediaType.FORM){
                    call = RetrofitClient.getServiceApi(config).post(headers, url, params);
                }else {
                    String paramsJsonStr = JSON.toJSONString(params);
                    StringBuilder sb = new StringBuilder("[").append(paramsJsonStr).append("]");
                    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),sb.toString());
                    call = RetrofitClient.getServiceApi(config).post(headers, url, requestBody);
                }
                return excute(call);
            }
        });
    }

    @Override
    public <T> Observable<T> post(HttpEnginerConfig config, String url, Map<String, String> headers, Map<String, String> params, final IConverter<T> converter) {
        return post(config, url, headers, params).map(new Function<String, T>() {
            @Override
            public T apply(String result) throws ParseException {
                if (!TextUtils.isEmpty(result)) {
                    try {
                        T t = converter.convert(result);
                        return t;
                    } catch (Exception e) {
                        throw new ParseException("解析错误", 0);
                    }
                }
                return null;
            }
        });
    }

    /*****************************下载文件**********************************/

    //异步下载文件，支持下载进度监听
    @Override
    public void downloadAsync(Context context, HttpEnginerConfig config, String url, String saveRootPath, String fileName,OnLoadProgressListener listener) {
        new DownLoadManager(context).downloadAsnyc(config, url, saveRootPath,fileName, listener);
    }

    //同步下载文件，兼容Rxjava
    @Override
    public Observable<ProgressInfo> download(Context context, HttpEnginerConfig config, String url, String saveRootPath, String fileName) {
        return new DownLoadManager(context).downLoad(config, url, saveRootPath ,fileName);
    }

    /*********************************上传文件**********************************/

    /**
     * 上传文件,异步操作
     *
     * @param config
     * @param url
     * @param headers
     * @param params
     * @param callback
     * @param listener
     * @param files
     */
    @Override
    public void uploadAsync(HttpEnginerConfig config, String url, Map<String, String> headers, Map<String, String> params, IHttpCallback callback, OnLoadProgressListener listener, File... files) {
        RequestBody realRequestBody = null;
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.addFormDataPart(entry.getKey(), entry.getValue());
        }
        for (File file : files) {
            RequestBody requestBody = RequestBody.create(MediaType.parse(FileUtil.getMIME(file.getAbsolutePath())), file);
            builder.addFormDataPart("file", file.getName(), requestBody);
        }
        if (listener != null) {
            realRequestBody = new ExMultipartBody(builder.build(), listener);
        } else {
            realRequestBody = builder.build();
        }
        call = RetrofitClient.getServiceApi(config).upload(headers, url, realRequestBody);
        enqueue(call, callback);
    }

    @Override
    public Observable<ProgressInfo> upload(final HttpEnginerConfig config, final String url, final Map<String, String> headers, final Map<String, String> params, final File... files) {
        return Observable.create(new ObservableOnSubscribe<ProgressInfo>() {
            @Override
            public void subscribe(ObservableEmitter<ProgressInfo> emitter) throws Exception {
                try {
                    MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                    for (Map.Entry<String, String> entry : params.entrySet()) {
                        builder.addFormDataPart(entry.getKey(), entry.getValue());
                    }
                    for (File file : files) {
                        RequestBody requestBody = RequestBody.create(MediaType.parse(FileUtil.getMIME(file.getAbsolutePath())), file);
                        builder.addFormDataPart("file", file.getName(), requestBody);
                    }
                    ExMultipartBody realRequestBody = new ExMultipartBody(builder.build(), emitter);
                    call = RetrofitClient.getServiceApi(config).upload(headers, url, realRequestBody);
                    Response<ResponseBody> response = call.execute();
                    if (response.isSuccessful()) {
                        if (emitter.isDisposed()) return;
                        emitter.onComplete();
                    } else {
                        throw new HttpException(response.code(), response.message());
                    }
                } catch (IOException e) {
                    if (call != null) {
                        call.cancel();
                    }
                    RxJavaPlugins.setErrorHandler(new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            Log.e("TAG", "异常交给全局处理");
                        }
                    });
                    throw e;
                }
            }
        });
    }

    /**
     * 同步请求
     *
     * @param call
     * @return
     * @throws IOException
     */
    private String excute(Call call) throws IOException {
        String result = "";
        Response<ResponseBody> response = null;
        try {
            response = call.execute();
            if (response.isSuccessful()) {
                result = response.body().string();
            } else {
                //网络可正常请求，但是服务端返回错误
                throw new HttpException(response.code(), response.message());
            }
        } catch (IOException e) {
            //网络请求出现错误（连接超时,位置主机等错误），此时则取消请求
            call.cancel();
                    /*此处设置这个错误处理器的作用是将一些异常交给流的全局处理的时候不会报错，比如当刚请求网络即关闭
                    页面的情况下会出现InterruptedIOException异常，此时将异常抛出后查看源码执行的是以下代码：
                    if (!d.isDisposed()) {
                        observer.onError(e);
                    } else {
                        RxJavaPlugins.onError(e);
                    }
                    因为关闭页面我们处理后导致d.isDisposed()=true,不会交给正常的流进处理，所以会将异常交给RxJavaPlugins
                   如果不设置错误处理器那么就将异常抛给了虚拟机导致APP崩溃，这个设置针对的是特殊情况。
                    */
            RxJavaPlugins.setErrorHandler(new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    Log.e("TAG", "异常交给全局处理");
                }
            });
            throw e;
        }
        return result;
    }

    /**
     * 异步请求
     *
     * @param call
     * @param httpCallback
     */
    public void enqueue(Call call, final IHttpCallback httpCallback) {
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    String result = "";
                    try {
                        result = response.body().string();
                        httpCallback.onSuccess(result);
                    } catch (IOException e) {
                        onFailure(call, new IOStreamReadException());
                    }
                } else {
                    onFailure(call, new HttpException(response.code(), response.message()));
                }
                httpCallback.onFinish();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                call.cancel();
                httpCallback.onFailure(t);
                httpCallback.onFinish();
            }
        });
    }


}
