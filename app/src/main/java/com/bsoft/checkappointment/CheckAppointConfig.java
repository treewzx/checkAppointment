package com.bsoft.checkappointment;

import android.app.Application;

import com.bsoft.baselib.http.HttpEnginer;
import com.bsoft.baselib.http.HttpEnginerConfig;
import com.bsoft.baselib.http.RequestParamMap;
import com.bsoft.baselib.http.request.retrofit.RetrofitRequest;
import com.bsoft.checkappointment.callback.OnOpenModuleListener;
import com.bsoft.checkappointment.callback.OnSelectPatientListener;
import com.bsoft.checkappointment.model.AccountInfo;
import com.bsoft.checkappointment.model.ChoosedFamliyVo;
import com.bsoft.common.utils.ToastUtil;

import java.util.Map;

import butterknife.OnClick;
import io.reactivex.functions.BiFunction;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/5/29.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
public class CheckAppointConfig {

    private static volatile CheckAppointConfig instance;
    private OnSelectPatientListener mOnSelectPatientListener;
    private OnOpenModuleListener mOnOpenModuleListener;
    //private OnPay

    public static CheckAppointConfig getInstance() {
        if (instance == null) {
            synchronized (CheckAppointConfig.class) {
                if (instance == null) {
                    instance = new CheckAppointConfig();
                }
            }
        }
        return instance;
    }

    //第一步
    public CheckAppointConfig withContext(Application application) {
        MyApplication.setInstance(application);
        return this;
    }

    /**
     * 设置网络
     *
     * @param baseUrl   基本url
     * @param isDebug   网络是否打印debug日志
     * @param mediaType 请求的提交方式Form表单、Json
     * @return
     */
    public CheckAppointConfig setNetConfig(String baseUrl, boolean isDebug, HttpEnginerConfig.MediaType mediaType) {
        HttpEnginerConfig httpEnginerConfig = new HttpEnginerConfig.Builder()
                .httpRequest(new RetrofitRequest())
                .baseUrl(baseUrl)
                .mediaType(mediaType)
                .debug(isDebug)
                .build();
        HttpEnginer.init(httpEnginerConfig);
        /*HttpEnginer.setOnInterceptFunction(new BiFunction<Map<String, String>, Map<String, String>, RequestParamMap>() {
            @Override
            public RequestParamMap apply(Map<String, String> headers, Map<String, String> params) throws Exception {
                //添加公共请求头和公共参数

                RequestParamMap paramMap = new RequestParamMap(headers, params);
                return paramMap;
            }
        });*/
        return this;
    }

    public CheckAppointConfig setOnSelectPatientListener(OnSelectPatientListener onSelectPatientListener) {
        this.mOnSelectPatientListener = onSelectPatientListener;
        return this;
    }

    public CheckAppointConfig setOnOpenModuleListener(OnOpenModuleListener onOpenModuleListener) {
        this.mOnOpenModuleListener = onOpenModuleListener;
        return this;
    }

    public OnSelectPatientListener getOnSelectPatientListener() {
        return mOnSelectPatientListener;
    }

    public OnOpenModuleListener getOnOpenModuleListener() {
        return mOnOpenModuleListener;
    }


}
