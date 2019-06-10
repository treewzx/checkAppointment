package com.bsoft.checkappointment.common;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bsoft.baselib.http.HttpEnginer;
import com.bsoft.baselib.http.exception.ApiException;
import com.bsoft.baselib.utils.RxUtil;
import com.bsoft.checkappointment.R;
import com.bsoft.checkappointment.model.AppointDateVo;
import com.bsoft.checkappointment.model.AppointTimeVo;
import com.bsoft.checkappointment.model.PatientAppointmentVo;
import com.bsoft.common.activity.BaseActivity;
import com.bsoft.common.adapter.CommonAdapter;
import com.bsoft.common.adapter.ViewHolder;
import com.bsoft.common.http.BaseObserver;
import com.bsoft.common.http.ResultConverter;
import com.bsoft.common.utils.DateUtil;
import com.bsoft.common.utils.ToastUtil;
import com.bsoft.common.view.roundview.RoundLinearLayout;
import com.bsoft.common.view.roundview.RoundViewDelegate;

import java.util.ArrayList;
import java.util.List;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/6/3.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
public class ChooseAppointTimeActivity extends BaseActivity {
    private List<AppointDateVo> mDateList = new ArrayList<>();
    private List<AppointTimeVo> mTimeList = new ArrayList<>();
    private CommonAdapter<AppointDateVo> mDateAdapter;
    private CommonAdapter<AppointTimeVo> mTimeAdapter;
    private AppointTimeVo mSelectedAppointTimeVo;
    private int mSelectDatePosition;
    private int mSelectTimePosition = -1;
    private PatientAppointmentVo mAppointVo;
    private TextView mAppointItemTv;
    private TextView mPreviousAppointTimeTv;
    private boolean mIsReAppoint;

    @Override
    public int getContentViewId(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_choose_appoint_time;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mPreviousAppointTimeTv = findViewById(R.id.previous_appointment_tv);
        mAppointItemTv = findViewById(R.id.appointment_item_tv);
        mAppointVo = getIntent().getParcelableExtra("appointmentItem");
        mIsReAppoint = getIntent().getBooleanExtra("isReAppoint", false);
        if (mIsReAppoint) {
            initDefaultToolbar("重新选号");
            mPreviousAppointTimeTv.setVisibility(View.VISIBLE);
            String checkTime = new StringBuilder()
                    .append(DateUtil.getYMDHM(mAppointVo.getCheckStartTime()))
                    .append("-")
                    .append(DateUtil.getHM(mAppointVo.getCheckEndTime()))
                    .toString();
            mPreviousAppointTimeTv.setText(new StringBuilder("前次预约时间：").append(checkTime).toString());
        } else {
            initDefaultToolbar("选择号源");
            mPreviousAppointTimeTv.setVisibility(View.GONE);
        }
        mAppointItemTv.setText(mAppointVo.getCheckItemName());

        setClick();

        initDateAdapter();
        initTimeAdapter();

        getAppointDate();
    }

    private void setClick() {
        //进行预约
        findViewById(R.id.appointment_excute_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelectedAppointTimeVo != null) {
                    Intent intent = null;
                    if (mIsReAppoint) {
                        intent = new Intent(ChooseAppointTimeActivity.this, ReAppointActivity.class);
                    } else {
                        intent = new Intent(ChooseAppointTimeActivity.this, SubmitAppointActivity.class);
                    }
                    intent.putExtra("appointmentItem", mAppointVo)
                            .putExtra("appointTime", mSelectedAppointTimeVo);
                    startActivity(intent);
                } else {
                    ToastUtil.showShort("请选择预约时间");
                }
            }
        });
    }

    private void initDateAdapter() {
        mDateAdapter = new CommonAdapter<AppointDateVo>(this, R.layout.recycle_item_choose_appoint_date, mDateList) {
            @Override
            protected void convert(ViewHolder holder, AppointDateVo appointDateVo, int position) {
                holder.setText(R.id.week_tv, appointDateVo.getDayOfTheWeek())
                        .setText(R.id.date_tv, DateUtil.getDay(appointDateVo.getAppointmentDate()))
                        .setVisible(R.id.indicator_view, mSelectDatePosition == position);
                if (mSelectDatePosition == position) {
                    getAppointTime(appointDateVo.getAppointmentDate());
                    holder.setTextColorRes(R.id.date_tv, R.color.main)
                            .setTypeface(Typeface.defaultFromStyle(Typeface.BOLD), R.id.date_tv);
                } else {
                    holder.setTextColorRes(R.id.date_tv, android.R.color.black)
                            .setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL), R.id.date_tv);
                }
                holder.getConvertView().setOnClickListener(v -> {
                    mSelectDatePosition = holder.getAdapterPosition();
                    mSelectTimePosition = -1;
                    mSelectedAppointTimeVo = null;
                    notifyDataSetChanged();
                });
            }
        };
        RecyclerView mDateRv = findViewById(R.id.date_rv);
        mDateRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mDateRv.setAdapter(mDateAdapter);
    }

    private void initTimeAdapter() {
        mTimeAdapter = new CommonAdapter<AppointTimeVo>(this, R.layout.recycle_item_choose_appoint_time, mTimeList) {
            @Override
            protected void convert(ViewHolder holder, AppointTimeVo appointTimeVo, int position) {
                //设置布局内容
                String appointTime = new StringBuilder()
                        .append(DateUtil.getHM(appointTimeVo.getNumberStartTime()))
                        .append("-")
                        .append(DateUtil.getHM(appointTimeVo.getNumberEndTime()))
                        .toString();
                String numCount = new StringBuilder()
                        .append(appointTimeVo.getRemainNumberCount())
                        .append("/")
                        .append(appointTimeVo.getTotalNumberCount())
                        .toString();
                holder.setText(R.id.time_tv, appointTime)
                        .setText(R.id.num_state_tv, appointTimeVo.getRemainNumberCount() > 0 ? "有号" : "约满")
                        .setText(R.id.num_count_tv, numCount);

                //设置布局背景颜色等样式
                RoundViewDelegate roundViewDelegate = ((RoundLinearLayout) holder.getView(R.id.appointment_time_rll)).getDelegate();
                roundViewDelegate.setStrokeColor(fetchColor(R.color.main));
                if (mSelectTimePosition == position) {
                    holder.setTextColorRes(android.R.color.white, R.id.time_tv, R.id.num_state_tv, R.id.num_count_tv);
                    roundViewDelegate.setBackgroundColor(fetchColor(R.color.main));
                } else {
                    holder.setTextColorRes(android.R.color.black, R.id.time_tv, R.id.num_state_tv)
                            .setTextColorRes(R.id.num_count_tv, R.color.main);
                    roundViewDelegate.setBackgroundColor(fetchColor(R.color.white));
                }
                if (appointTimeVo.getRemainNumberCount() == 0) {
                    roundViewDelegate.setStrokeColor(fetchColor(android.R.color.darker_gray));
                    holder.setTextColorRes(android.R.color.darker_gray, R.id.time_tv, R.id.num_state_tv, R.id.num_count_tv);
                }
                //前一次的预约时间和显示的时间比较确认上次选中的时间
                if (DateUtil.isSameDay(mDateList.get(mSelectDatePosition).getAppointmentDate(), mAppointVo.getCheckStartTime())) {
                    //是同一天然后判断日期
                    if (DateUtil.isFirstBeforeSecond(appointTimeVo.getNumberStartTime(), mAppointVo.getCheckStartTime())
                            && DateUtil.isFirstBeforeSecond(mAppointVo.getCheckStartTime(), appointTimeVo.getNumberEndTime())) {
                        roundViewDelegate.setStrokeColor(fetchColor(android.R.color.holo_orange_dark));
                        appointTimeVo.setPreviousSelected(true);
                        if (appointTimeVo.getRemainNumberCount() != 0) {
                            holder.setTextColorRes(R.id.num_count_tv, android.R.color.holo_orange_dark);
                        }
                    }
                } else {
                    appointTimeVo.setPreviousSelected(false);
                }


                //设置点击事件
                holder.getConvertView().setOnClickListener(v -> {
                    if (mTimeList.get(holder.getAdapterPosition()).getRemainNumberCount() != 0 && !(mTimeList.get(holder.getAdapterPosition()).isPreviousSelected())) {
                        mSelectTimePosition = holder.getAdapterPosition();
                        mSelectedAppointTimeVo = mTimeList.get(mSelectTimePosition);
                        notifyDataSetChanged();
                    }
                });
            }
        };
        RecyclerView mTimeRv = findViewById(R.id.time_rv);
        mTimeRv.setLayoutManager(new GridLayoutManager(this, 3));
        mTimeRv.setAdapter(mTimeAdapter);
    }

    private void getAppointDate() {
        HttpEnginer.newInstance()
                .addUrl("auth/checkAppointment/getAppointableDate")
                .addParam("hospitalCode", "A00001")
                .addParam("checkRequestNumber", "343254345")
                .addParam("checkItemCode", "MRI")
                .addParam("checkItemName", "MRI")
                .post(new ResultConverter<List<AppointDateVo>>() {
                })
                .compose(RxUtil.applyLifecycleLCESchedulers(this, this))
                .subscribe(new BaseObserver<List<AppointDateVo>>() {
                    @Override
                    public void onFail(ApiException exception) {

                    }

                    @Override
                    public void onNext(List<AppointDateVo> appointDateVos) {
                        mDateList.clear();
                        mDateList.addAll(appointDateVos);
                        mDateAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void getAppointTime(String selectDate) {
        Log.e("TAG", DateUtil.getYMD(selectDate));
        HttpEnginer.newInstance()
                .addUrl("auth/checkAppointment/getAppointableDateTime")
                .addParam("hospitalCode", "A00001")
                .addParam("checkRequestNumber", "343254345")
                .addParam("checkItemCode", "MRI")
                .addParam("checkItemName", "MRI")
                .addParam("appointmentDate", DateUtil.getYMD(selectDate))
                .post(new ResultConverter<List<AppointTimeVo>>() {
                })
                .compose(RxUtil.applyLifecycleLCESchedulers(this, this))
                .subscribe(new BaseObserver<List<AppointTimeVo>>() {
                    @Override
                    public void onFail(ApiException exception) {

                    }

                    @Override
                    public void onNext(List<AppointTimeVo> appointTimeVos) {
                        mTimeList.clear();
                        mTimeList.addAll(appointTimeVos);
                        mTimeAdapter.notifyDataSetChanged();
                    }
                });
    }
}
