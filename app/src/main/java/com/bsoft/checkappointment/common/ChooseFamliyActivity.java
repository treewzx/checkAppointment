package com.bsoft.checkappointment.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bsoft.baselib.http.HttpEnginer;
import com.bsoft.baselib.http.HttpEnginerConfig;
import com.bsoft.baselib.http.exception.ApiException;
import com.bsoft.baselib.http.httpcallback.HttpCallback;
import com.bsoft.baselib.utils.RxUtil;
import com.bsoft.checkappointment.CheckAppointConfig;
import com.bsoft.checkappointment.Const;
import com.bsoft.checkappointment.MyApplication;
import com.bsoft.checkappointment.R;
import com.bsoft.checkappointment.callback.OnSelectPatientListener;
import com.bsoft.checkappointment.callback.SelectPatientCallback;
import com.bsoft.checkappointment.model.PatientInfo;
import com.bsoft.checkappointment.model.SystemConfigVo;
import com.bsoft.checkappointment.operationsearch.OperationSearchActivity;
import com.bsoft.checkappointment.outpatients.activity.OutPatientsAppointActivity;
import com.bsoft.common.activity.BaseActivity;
import com.bsoft.common.http.BaseObserver;
import com.bsoft.common.http.ResultConverter;
import com.bsoft.common.model.LoginUserVo;
import com.bsoft.common.utils.DateUtil;
import com.bsoft.common.utils.DeviceUtil;
import com.bsoft.common.utils.MD5;
import com.bsoft.common.utils.ToastUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public class ChooseFamliyActivity extends BaseActivity {
    private LinearLayout mOutpatientsAppointLl;
    private LinearLayout mInpatientsAppointLl;
    private TextView mPatientNameTv;
    private PatientInfo mPatientInfo;

    private SelectPatientCallback callback;

    @Override
    public int getContentViewId(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_choose_famliy_checkappoint;
    }

    private void initView() {
        mOutpatientsAppointLl = findViewById(R.id.outpatients_appoint_ll);
        mInpatientsAppointLl = findViewById(R.id.inpatients_appoint_ll);
        mPatientNameTv = findViewById(R.id.patient_name_tv);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initDefaultToolbar("检查预约");
        initView();
        setClick();
        String timestamp = String.valueOf(System.currentTimeMillis());
        String utype = "1"; //1-居民，2-医生，3-管理人员
        String device = DeviceUtil.getDeviceId(this);
        HttpEnginer.newInstance()
                .addUrl("login")
                .setInterceptRequestParams(false)
                .addParam("username", "17682304296")
                .addParam("password", MD5.getMD5("qwerty123"))
                .addParam("utype", utype)
                .addParam("device", device)
                .addParam("timestamp", timestamp)
                .addParam("token", "")
                .addParam("sn", "")
                .post(new ResultConverter<LoginUserVo>() {
                })
                .flatMap(new Function<LoginUserVo, ObservableSource<List<SystemConfigVo>>>() {
                    @Override
                    public ObservableSource<List<SystemConfigVo>> apply(LoginUserVo loginUserVo) throws Exception {
                        MyApplication.loginUserVo = loginUserVo;
                        MyApplication.sn = loginUserVo.sn;
                        MyApplication.token = loginUserVo.token;
                        return HttpEnginer.newInstance()
                                .addUrl("auth/sysParameter/getSysParameterList")
                                .addParam("parameterModuleId", "021000")
                                .post(new ResultConverter<List<SystemConfigVo>>() {
                                });
                    }
                })
                .compose(RxUtil.applyLifecycleLCESchedulers(this, this))
                .subscribe(new BaseObserver<List<SystemConfigVo>>() {
                    @Override
                    public void onFail(ApiException exception) {

                    }

                    @Override
                    public void onNext(List<SystemConfigVo> systemConfigVos) {
                        Const.systemConfigMap.clear();
                        for (SystemConfigVo systemConfigVo : systemConfigVos) {
                            Const.systemConfigMap.put(systemConfigVo.getParameterKey(), systemConfigVo.getParameterValue());
                        }
                    }
                });

    }


    public void setClick() {
        mOutpatientsAppointLl.setOnClickListener(v -> {
            if (TextUtils.isEmpty(mPatientNameTv.getText())) {
                ToastUtil.showShort("请选择就诊人");
            } else {
                Const.patientType = 1;
            }
            startActivity(new Intent(this, OutPatientsAppointActivity.class));
        });
        mInpatientsAppointLl.setOnClickListener(v -> {
            if (TextUtils.isEmpty(mPatientNameTv.getText())) {
                ToastUtil.showShort("请选择就诊人");
            } else {
                Const.patientType = 2;

            }
            startActivity(new Intent(this, OperationSearchActivity.class));
        });
        findViewById(R.id.choose_famliy_ll).setOnClickListener(v -> {
            CheckAppointConfig.getInstance()
                    .getOnSelectPatientListener()
                    .onSelectPatient(new SelectPatientCallback() {
                        @Override
                        public void onSelectReceiver(PatientInfo patientInfo) {
                            ChooseFamliyActivity.this.mPatientInfo = patientInfo;
                            mPatientNameTv.setText(patientInfo.getPatientName());
                        }
                    });
        });


    }
}
