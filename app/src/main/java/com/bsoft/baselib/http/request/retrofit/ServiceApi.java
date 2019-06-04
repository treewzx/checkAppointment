package com.bsoft.baselib.http.request.retrofit;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/3/11.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
public interface ServiceApi {

    @FormUrlEncoded
    @POST()
    Call<ResponseBody> post(@Url String url, @FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST()
    Call<ResponseBody> post(@HeaderMap Map<String, String> headersMap, @Url String url, @FieldMap Map<String, String> params);

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST()
    Call<ResponseBody> post(@Url String url, @Body RequestBody requestBody);

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST()
    Call<ResponseBody> post(@HeaderMap Map<String, String> headersMap, @Url String url, @Body RequestBody requestBody);

    @GET()
    Call<ResponseBody> get(@Url String url, @QueryMap Map<String, String> params);

    @GET()
    Call<ResponseBody> get(@HeaderMap Map<String, String> headersMap, @Url String url, @QueryMap Map<String, String> params);

    @Streaming
    @GET
    Call<ResponseBody> download(@Header("RANGE") String start, @Url String url);

    @POST
    Call<ResponseBody> upload(@HeaderMap Map<String, String> headersMap, @Url String url, @Body RequestBody body);

}
