package com.bsoft.checkappointment.inpatients.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.bsoft.checkappointment.R;
import com.bsoft.checkappointment.inpatients.fragment.InPatientAppointedFragment;
import com.bsoft.checkappointment.inpatients.fragment.InPatientUnappointFragment;
import com.bsoft.common.activity.BaseActivity;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/6/11.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
public class InPatientAppointActivity extends BaseActivity {
    private ViewPager viewPager;
    private SlidingTabLayout tabLayout;


    private String[] titles = new String[]{"待预约", "已预约"};


    @Override
    public int getContentViewId(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_patients_checkappoint;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initDefaultToolbar("住院检查预约");
        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.title_tablayout);
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new InPatientUnappointFragment());
        fragments.add(new InPatientAppointedFragment());
        tabLayout.setViewPager(viewPager, titles, this, fragments);
    }
}
