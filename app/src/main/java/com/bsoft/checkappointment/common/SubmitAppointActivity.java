package com.bsoft.checkappointment.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bsoft.baselib.http.HttpEnginer;
import com.bsoft.baselib.http.exception.ApiException;
import com.bsoft.baselib.utils.RxUtil;
import com.bsoft.checkappointment.R;
import com.bsoft.checkappointment.model.AppointTimeVo;
import com.bsoft.checkappointment.model.PatientAppointmentVo;
import com.bsoft.common.activity.BaseActivity;
import com.bsoft.common.http.BaseObserver;
import com.bsoft.common.http.HttpBaseResultVo;
import com.bsoft.common.utils.DateUtil;
import com.bsoft.common.utils.ToastUtil;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/5/31.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
public class SubmitAppointActivity extends BaseAppointOrCancleActivity {

    @Override
    protected String getToolbarTitle() {
        return "预约确认";
    }

    @Override
    protected String getExcuteBtnText() {
        return "提交预约";
    }

    @Override
    protected void excuteTask() {
        submitApponitment();
    }

    private void submitApponitment() {
        // 需要进行互斥的判断
        HttpEnginer.newInstance()
                .addUrl("auth/checkAppointment/doReservationConfirmation")
                .addParam("hospitalCode", "A00001")
                .addParam("checkRequestNumber", "NO1559117412979R17876")
                .addParam("checkItemCode", "MRI")
                .addParam("checkItemName", "MRI")
                .addParam("appointmentDate", "2019-05-31")
                .addParam("appointmentQueueCode", "A01")
                .addParam("numberStartTime", "2019-05-31 09:00:00")
                .addParam("numberEndTime", "2019-05-31 10:00:00")
                .post()
                .compose(RxUtil.applyLifecycleLCESchedulers(this, this))
                .subscribe(new BaseObserver<String>() {
                    @Override
                    public void onFail(ApiException exception) {
                        ToastUtil.showShort(exception.getMessage());
                    }

                    @Override
                    public void onNext(String result) {
                        HttpBaseResultVo resultVo = JSON.parseObject(result, HttpBaseResultVo.class);
                        if (resultVo.code == 1 || resultVo.code == 200) {
                            Intent intent = new Intent(SubmitAppointActivity.this, AppointOrCancleResultActivity.class);
                            intent.putExtra("opType", 1);//预约
                            startActivity(intent);
                            SubmitAppointActivity.this.finish();
                        } else {
                            ToastUtil.showShort(resultVo.message);
                        }
                    }
                });
    }


}
