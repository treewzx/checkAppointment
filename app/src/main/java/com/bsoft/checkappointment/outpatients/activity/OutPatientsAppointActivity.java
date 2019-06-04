package com.bsoft.checkappointment.outpatients.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.bsoft.checkappointment.R;
import com.bsoft.checkappointment.outpatients.fragment.UnappointFragment;
import com.bsoft.checkappointment.outpatients.fragment.AppointedFragment;
import com.bsoft.common.activity.BaseActivity;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/5/29.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
public class OutPatientsAppointActivity extends BaseActivity {
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.title_tablayout)
    SlidingTabLayout tabLayout;

    private String[] titles = new String[]{"待预约", "已预约"};

    @Override
    public int getContentViewId(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_outpatients_checkappoint;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initDefaultToolbar("门诊检查预约");


        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new UnappointFragment());
        fragments.add(new AppointedFragment());
        tabLayout.setViewPager(viewPager, titles, this, fragments);
    }
}
