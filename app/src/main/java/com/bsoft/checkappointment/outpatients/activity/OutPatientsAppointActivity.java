package com.bsoft.checkappointment.outpatients.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.bsoft.baselib.framework.helper.EventBusManager;
import com.bsoft.checkappointment.R;
import com.bsoft.checkappointment.event.GoToAppointmentEvent;
import com.bsoft.checkappointment.outpatients.fragment.UnappointFragment;
import com.bsoft.checkappointment.outpatients.fragment.AppointedFragment;
import com.bsoft.common.activity.BaseActivity;
import com.flyco.tablayout.SlidingTabLayout;

import org.greenrobot.eventbus.EventBus;

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
    private ViewPager viewPager;
    private SlidingTabLayout tabLayout;


    private String[] titles = new String[]{"待预约", "已预约"};


    @Override
    public int getContentViewId(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_outpatients_checkappoint;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initDefaultToolbar("门诊检查预约");
        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.title_tablayout);
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new UnappointFragment());
        fragments.add(new AppointedFragment());
        tabLayout.setViewPager(viewPager, titles, this, fragments);
    }
}
