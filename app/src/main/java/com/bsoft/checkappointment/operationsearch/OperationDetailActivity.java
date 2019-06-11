package com.bsoft.checkappointment.operationsearch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.bsoft.baselib.http.HttpEnginer;
import com.bsoft.baselib.http.exception.ApiException;
import com.bsoft.baselib.utils.RxUtil;
import com.bsoft.checkappointment.MyApplication;
import com.bsoft.checkappointment.R;
import com.bsoft.checkappointment.model.OperationDetailVo;
import com.bsoft.checkappointment.model.OperationVo;
import com.bsoft.common.activity.BaseActivity;
import com.bsoft.common.http.BaseObserver;
import com.bsoft.common.http.ResultConverter;

import java.util.List;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/6/11.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
public class OperationDetailActivity extends BaseActivity {
    private TextView mPatientNameTv;
    private TextView mOperationStateTv;
    private TextView mOperationNameTv;
    private TextView mOperationTimeTv;
    private TextView mOperationRoomTv;
    private TextView mOperationLocationTv;
    private TextView mOperationDepartmentTv;
    private TextView mOperationNumTv;
    private TextView mOperationAnestheticMethodTv;
    private TextView mOperationDoctotTv;
    private TextView mAnesthesiologistTv;
    private TextView mOperationNurseTv;
    private TextView mOperationNoteTv;


    @Override
    public int getContentViewId(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_operation_detail;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initDefaultToolbar("手术详情");
        initView();
        String operationId = getIntent().getStringExtra("operationId");
        String patientName = getIntent().getStringExtra("patientName");
        mPatientNameTv.setText(patientName);
        HttpEnginer.newInstance()
                .addUrl("auth/operation/operationDetails")
                .addParam("hospitalCode", MyApplication.loginUserVo.getHospitalCode())
                .addParam("operationId", operationId)
                .post(new ResultConverter<OperationDetailVo>() {
                })
                .compose(RxUtil.applyLifecycleLCESchedulers(this, this))
                .subscribe(new BaseObserver<OperationDetailVo>() {
                    @Override
                    public void onFail(ApiException exception) {

                    }

                    @Override
                    public void onNext(OperationDetailVo operationDetailVo) {
                        mOperationStateTv.setText(operationDetailVo.getStatusStr());
                        mOperationNameTv.setText(operationDetailVo.getOperationName());
                        mOperationTimeTv.setText(operationDetailVo.getOperativeTime());
                        mOperationRoomTv.setText(operationDetailVo.getOperatingRoom());
                        mOperationLocationTv.setText(operationDetailVo.getOperativePlace());
                        mOperationDepartmentTv.setText(operationDetailVo.getDepartmentName());
                        mOperationNumTv.setText(operationDetailVo.getTableNumber());
                        mOperationAnestheticMethodTv.setText(operationDetailVo.getAnestheticMethods());
                        mOperationDoctotTv.setText(operationDetailVo.getSurgeon());
                        mAnesthesiologistTv.setText(operationDetailVo.getAnesthesiologist());
                        mOperationNurseTv.setText(operationDetailVo.getNurse());
                        mOperationNoteTv.setText(operationDetailVo.getConsiderations() != null ? operationDetailVo.getConsiderations() : "暂无注意事项");
                    }
                });
    }

    private void initView() {
        mPatientNameTv = findViewById(R.id.patient_name_tv);
        mOperationStateTv = findViewById(R.id.operation_state_tv);
        mOperationNameTv = findViewById(R.id.operation_name_tv);
        mOperationTimeTv = findViewById(R.id.operation_time_tv);
        mOperationRoomTv = findViewById(R.id.operation_room_tv);
        mOperationLocationTv = findViewById(R.id.operation_location_tv);
        mOperationDepartmentTv = findViewById(R.id.operation_department_tv);
        mOperationNumTv = findViewById(R.id.operation_num_tv);
        mOperationAnestheticMethodTv = findViewById(R.id.operation_anesthetic_method_tv);
        mOperationDoctotTv = findViewById(R.id.operation_doctor_tv);
        mAnesthesiologistTv = findViewById(R.id.anesthesiologist_tv);
        mOperationNurseTv = findViewById(R.id.operation_nurse_tv);
        mOperationNoteTv = findViewById(R.id.operation_note_tv);
    }
}
