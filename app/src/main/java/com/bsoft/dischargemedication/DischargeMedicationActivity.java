package com.bsoft.dischargemedication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.bsoft.baselib.http.HttpEnginer;
import com.bsoft.baselib.http.exception.ApiException;
import com.bsoft.baselib.utils.RxUtil;
import com.bsoft.checkappointment.R;
import com.bsoft.common.activity.BaseActivity;
import com.bsoft.common.adapter.CommonAdapter;
import com.bsoft.common.adapter.ViewHolder;
import com.bsoft.common.http.BaseObserver;
import com.bsoft.common.http.ResultConverter;
import com.bsoft.dischargemedication.model.DischargeMedicationChildVo;
import com.bsoft.dischargemedication.model.DischargeMedicationGroupVo;

import java.util.ArrayList;
import java.util.List;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/6/12.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
public class DischargeMedicationActivity extends BaseActivity {
    private RecyclerView mDischargeMedicationGroupRv;
    private List<DischargeMedicationGroupVo> mDischargeMedicationGroupList = new ArrayList<>();
    private CommonAdapter<DischargeMedicationGroupVo> mGroupAdapter;

    @Override
    public int getContentViewId(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_discharge_medication;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initDefaultToolbar("出院带药");
        initView();
        initAdapter();
        getDischargeMedicationList();


    }

    private void initView() {
        mDischargeMedicationGroupRv = findViewById(R.id.discharge_medication_group_rv);
    }

    private void initAdapter() {
        mGroupAdapter = new CommonAdapter<DischargeMedicationGroupVo>(this, R.layout.recycle_item_dischargemedication_group, mDischargeMedicationGroupList) {
            @Override
            protected void convert(ViewHolder holder, DischargeMedicationGroupVo dischargeMedicationGroupVo, int position) {
                holder.setVisible(R.id.child_rv, dischargeMedicationGroupVo.isExpanded)
                        .setVisible(R.id.patient_info_ll, dischargeMedicationGroupVo.isExpanded)
                        .setImageResource(R.id.arrow_iv, dischargeMedicationGroupVo.isExpanded ?
                                R.drawable.discharge_meidication_arrow_close : R.drawable.discharge_medication_arrow_open);
                holder.setText(R.id.item_hospital_name_tv, dischargeMedicationGroupVo.getHospitalName())
                        .setText(R.id.patient_name_tv, dischargeMedicationGroupVo.getPatientName() + dischargeMedicationGroupVo.getPatientAge());

                CommonAdapter<DischargeMedicationChildVo> childAdapter = new CommonAdapter<DischargeMedicationChildVo>(
                        mContext, R.layout.recycle_item_dischargemedication_child, dischargeMedicationGroupVo.getDetailsItems()) {

                    @Override
                    protected void convert(ViewHolder holder, DischargeMedicationChildVo dischargeMedicationChildVo, int position) {
                        String mehtodStr = new StringBuilder(dischargeMedicationChildVo.getDrugFrequency())
                                .append(" ")
                                .append(dischargeMedicationChildVo.getDrugConsumption())
                                .append(" ")
                                .append(dischargeMedicationChildVo.getDrugRoute())
                                .toString();
                        holder.setText(R.id.medication_name_tv, dischargeMedicationChildVo.getItemName())
                                .setText(R.id.medication_num_tv, dischargeMedicationChildVo.getItemNumber() + dischargeMedicationChildVo.getUnit())
                                .setText(R.id.medication_method_tv, mehtodStr);
                    }
                };
                RecyclerView childRv = holder.getView(R.id.child_rv);
                childRv.setLayoutManager(new LinearLayoutManager(mContext));
                childRv.setAdapter(childAdapter);

                holder.getConvertView().setOnClickListener(v -> {
                    dischargeMedicationGroupVo.isExpanded = !dischargeMedicationGroupVo.isExpanded;
                    if (mGroupAdapter != null) {
                        mGroupAdapter.notifyDataSetChanged();
                    }
                });
            }
        };
        mDischargeMedicationGroupRv.setLayoutManager(new LinearLayoutManager(this));
        mDischargeMedicationGroupRv.setAdapter(mGroupAdapter);
    }


    private void getDischargeMedicationList() {
        HttpEnginer.newInstance()
                .addUrl("auth/hospitalization/getDischargeMedicationInformation")
                .addParam("hospitalCode", "08767332")
                .addParam("patientCode", "323223")
                .addParam("patientName", "测试")
                .addParam("patientMedicalCardType", 2)
                .addParam("patientMedicalCardNumber", "A092424")
                .addParam("patientIdentityCardType", 1)
                .addParam("patientIdentityCardNumber", "37263819980909293X")
                .post(new ResultConverter<List<DischargeMedicationGroupVo>>() {
                })
                .compose(RxUtil.applyLifecycleLCESchedulers(this, this))
                .subscribe(new BaseObserver<List<DischargeMedicationGroupVo>>() {
                    @Override
                    public void onFail(ApiException exception) {

                    }

                    @Override
                    public void onNext(List<DischargeMedicationGroupVo> dischargeMedicationGroupVos) {
                        mDischargeMedicationGroupList.clear();
                        mDischargeMedicationGroupList.addAll(dischargeMedicationGroupVos);
                        mGroupAdapter.notifyDataSetChanged();
                    }
                });
    }
}
