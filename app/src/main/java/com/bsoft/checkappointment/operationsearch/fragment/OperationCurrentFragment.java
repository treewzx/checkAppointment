package com.bsoft.checkappointment.operationsearch.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.bsoft.baselib.framework.mvc.BaseMvcFragment;
import com.bsoft.baselib.http.HttpEnginer;
import com.bsoft.baselib.http.exception.ApiException;
import com.bsoft.baselib.utils.RxUtil;
import com.bsoft.checkappointment.MyApplication;
import com.bsoft.checkappointment.R;
import com.bsoft.checkappointment.model.OperationVo;
import com.bsoft.checkappointment.operationsearch.OperationDetailActivity;
import com.bsoft.checkappointment.operationsearch.OperationSearchActivity;
import com.bsoft.common.adapter.CommonAdapter;
import com.bsoft.common.adapter.ViewHolder;
import com.bsoft.common.fragment.BaseLazyLoadFragment;
import com.bsoft.common.http.BaseObserver;
import com.bsoft.common.http.ResultConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/6/11.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
public class OperationCurrentFragment extends BaseFragment {
    private CommonAdapter<OperationVo> mAdapter;

    private List<OperationVo> mOperationList = new ArrayList<>(2);

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        initAdapter();
    }


    @Override
    protected void loadData() {
        HttpEnginer.newInstance()
                .addUrl("auth/operation/operationListQuery")
                .addParam("hospitalCode", MyApplication.loginUserVo.getHospitalCode())
                .addParam("patientCodeList", "8888888")
                .addParam("status", 1)
                .post(new ResultConverter<List<OperationVo>>() {
                })
                .compose(RxUtil.applyLifecycleLCESchedulers(this, this))
                .subscribe(new BaseObserver<List<OperationVo>>() {
                    @Override
                    public void onFail(ApiException exception) {
                        showError();
                    }

                    @Override
                    public void onNext(List<OperationVo> operationVos) {
                        mOperationList.clear();
                        mOperationList.addAll(operationVos);
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public int getContentViewId(@Nullable Bundle savedInstanceState) {
        return R.layout.fragment_patients_appoint;
    }

    private void initAdapter() {
        if (recyclerView != null) {
            mAdapter = new CommonAdapter<OperationVo>(mContext, R.layout.recycle_item_operation_current, mOperationList) {
                @Override
                protected void convert(ViewHolder holder, OperationVo operationVo, int position) {
                    holder.setText(R.id.operation_department_tv, operationVo.getDepartmentName())
                            .setText(R.id.operation_name_tv, operationVo.getOperationName())
                            .setText(R.id.operation_state_tv, operationVo.getStatusStr())
                            .setText(R.id.operation_room_tv, operationVo.getOperatingRoom())
                            .setText(R.id.operation_time_tv, getString(R.string.operation_time, operationVo.getOperativeTime()))
                            .setText(R.id.operation_location_tv, getString(R.string.operation_time, operationVo.getOperativePlace()));
                    holder.getConvertView().setOnClickListener(v -> {
                        //进入详情页面
                        Intent intent = new Intent(mContext, OperationDetailActivity.class);
                        intent.putExtra("operationId", operationVo.getOperationId())
                                .putExtra("patientName", operationVo.getPatientName());
                        startActivity(intent);

                    });
                }
            };
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            recyclerView.setAdapter(mAdapter);
        }

    }
}
