package com.bsoft.checkappointment;

import android.app.Application;
import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.bsoft.baselib.http.HttpEnginer;
import com.bsoft.baselib.http.HttpEnginerConfig;
import com.bsoft.baselib.http.RequestParamMap;
import com.bsoft.baselib.http.request.retrofit.RetrofitRequest;
import com.bsoft.common.model.LoginUserVo;
import com.bsoft.common.utils.DeviceUtil;
import com.bsoft.common.utils.MD5;
import com.bsoft.common.utils.SizeUtil;
import com.bsoft.common.utils.ToastUtil;

import java.net.URLEncoder;
import java.util.Map;
import java.util.TreeMap;

import io.reactivex.functions.BiFunction;


/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/5/29.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
public class MyApplication extends Application {
    public static Application app;
    public static String token = "";
    public static String sn = "";
    HttpEnginerConfig httpEnginerConfig;
    public static LoginUserVo loginUserVo;

    @Override
    public void onCreate() {
        super.onCreate();
        SizeUtil.setContext(this);
        ToastUtil.init(this);
        httpEnginerConfig = new HttpEnginerConfig.Builder()
                .httpRequest(new RetrofitRequest())
                .debug(true)
                .baseUrl("http://wxtest01.atag.bsoft.com.cn/base-service/api/")
                .build();
        HttpEnginer.init(httpEnginerConfig);
        HttpEnginer.setOnInterceptFunction(new BiFunction<Map<String, String>, Map<String, String>, RequestParamMap>() {
            @Override
            public RequestParamMap apply(Map<String, String> headersMap, Map<String, String> paramsMap) throws Exception {
                httpEnginerConfig = httpEnginerConfig.newBuilder().mediaType(HttpEnginerConfig.MediaType.JSON).build();
                HttpEnginer.init(httpEnginerConfig);
                String timestamp = String.valueOf(System.currentTimeMillis());
                String utype = "1"; //1-居民，2-医生，3-管理人员
                String device = DeviceUtil.getDeviceId(MyApplication.this);
                //
                TreeMap<String, String> signTreeMap = new TreeMap<>();
                signTreeMap.putAll(paramsMap);
                StringBuilder sb = new StringBuilder();
                for (String key : signTreeMap.keySet()) {
                    sb.append(signTreeMap.get(key));
                }
                headersMap.put("sign", MD5.getMD5(sb.toString()));
                headersMap.put("timestamp", timestamp);
                headersMap.put("utype", URLEncoder.encode(utype, "utf-8"));
                headersMap.put("device", URLEncoder.encode(device, "utf-8"));
                headersMap.put("token", URLEncoder.encode(token, "utf-8"));
                headersMap.put("sn", URLEncoder.encode(sn, "utf-8"));
                return new RequestParamMap(headersMap, paramsMap);
            }
        });


    }

    public static Context setInstance(Application appa) {
        app = appa;
        ToastUtil.init(app);
        return app;
    }

    public static Context getInstance() {
        return app;
    }
}
