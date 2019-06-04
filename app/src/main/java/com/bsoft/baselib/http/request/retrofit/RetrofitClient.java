package com.bsoft.baselib.http.request.retrofit;


import com.bsoft.baselib.http.HttpEnginerConfig;
import com.bsoft.baselib.http.request.retrofit.interceptor.NullInterceptor;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/3/11.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
public class RetrofitClient {
    private static ServiceApi mServiceApi;

    public static ServiceApi getServiceApi(HttpEnginerConfig config) {
        if (mServiceApi == null) {
            X509TrustManager trustAllCert = new X509TrustManagerImpl();
            SSLSocketFactory sslSocketFactory = new SSLSocketFactoryImpl(trustAllCert);
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(config.getReadTimeout(), config.getTimeoutUnit())
                    .writeTimeout(config.getWriteTimeout(), config.getTimeoutUnit())
                    .connectTimeout(config.getConnectTimeout(), config.getTimeoutUnit())
                   // .sslSocketFactory(sslSocketFactory, trustAllCert) //添加信任证书
                    .addNetworkInterceptor((config.isDebug()) ? (new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)) : new NullInterceptor())
                    .build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(config.getBaseUrl())
                    .client(okHttpClient)
                    .build();

            mServiceApi = retrofit.create(ServiceApi.class);
        }
        return mServiceApi;
    }

}
