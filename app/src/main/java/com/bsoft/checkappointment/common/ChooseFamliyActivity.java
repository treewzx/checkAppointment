package com.bsoft.checkappointment.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.bsoft.checkappointment.MyApplication;
import com.bsoft.checkappointment.R;
import com.bsoft.checkappointment.outpatients.activity.OutPatientsAppointActivity;
import com.bsoft.common.activity.BaseActivity;
import com.bsoft.common.http.BaseObserver;
import com.bsoft.common.http.ResultConverter;
import com.bsoft.common.model.LoginUserVo;
import com.bsoft.common.utils.DateUtil;
import com.bsoft.common.utils.DeviceUtil;
import com.bsoft.common.utils.MD5;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

public class ChooseFamliyActivity extends BaseActivity {
    @BindView(R.id.outpatients_appoint_ll)
    LinearLayout mOutpatientsAppointLl;
    @BindView(R.id.inpatients_appoint_ll)
    LinearLayout mInpatientsAppointLl;
    @BindView(R.id.patient_name_tv)
    TextView mPatientNameTv;

    @Override
    public int getContentViewId(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_choose_famliy_checkappoint;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initDefaultToolbar("检查预约");
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
                .compose(RxUtil.applyLifecycleLCESchedulers(this, this))
                .subscribe(new BaseObserver<LoginUserVo>() {
                    @Override
                    public void onFail(ApiException exception) {
                        Log.e("TAG", exception.getMessage());
                    }

                    @Override
                    public void onNext(LoginUserVo loginUserVo) {
                        MyApplication.loginUserVo = loginUserVo;
                        MyApplication.sn = loginUserVo.sn;
                        MyApplication.token = loginUserVo.token;
                    }
                });

    }

    @Override
    protected void onStart() {
        super.onStart();




       /* CheckAppointConfig.OnChangeFamliyListener  onChangeFamliyListener = CheckAppointConfig.getInstance().getOnChangeFamliyListener();
        if(onChangeFamliyListener!=null){
            if(onChangeFamliyListener.onChangeFamliy()){
                mPatientNameTv.setText("刘生");
            }
        }*/

    }

    @OnClick({R.id.outpatients_appoint_ll, R.id.inpatients_appoint_ll, R.id.choose_famliy_ll})
    public void setClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.outpatients_appoint_ll) {
            startActivity(new Intent(this, OutPatientsAppointActivity.class));

        } else if (viewId == R.id.inpatients_appoint_ll) {

        } else if (viewId == R.id.choose_famliy_ll) {

        }


    }
}
