package com.bsoft.baselib.framework.mvc;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.List;


/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/4/25.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
public abstract class BaseMvcLazyLoadFragment extends BaseMvcFragment {
    private boolean mIsViewCreated; // 界面是否已创建完成
    private boolean mIsVisibleToUser; // 是否对用户可见
    private boolean mIsDataLoaded; // 数据是否已加载



    /**
     * 当fragment结合viewpager使用的时候 这个方法会调用
     * 这个方法是在oncreateView之前使用 不能在此方法内使用控件
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.mIsVisibleToUser = isVisibleToUser;
        lazyLoadData();
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    /**
     * 保证在initData后触发
     */
    @Override
    public void onResume() {
        super.onResume();
        mIsViewCreated = true;
        lazyLoadData();
    }

    /**
     * ViewPager场景下，判断父fragment是否可见
     */
    private boolean isParentVisible() {
        Fragment fragment = getParentFragment();
        return fragment == null || (fragment instanceof BaseMvcLazyLoadFragment && ((BaseMvcLazyLoadFragment) fragment).mIsVisibleToUser);
    }

    /**
     * ViewPager场景下，当前fragment可见时，如果其子fragment也可见，则让子fragment请求数据
     */
    private void dispatchParentVisibleState() {
        FragmentManager fragmentManager = getChildFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments.isEmpty()) {
            return;
        }
        for (Fragment child : fragments) {
            if (child instanceof BaseMvcLazyLoadFragment && ((BaseMvcLazyLoadFragment) child).mIsVisibleToUser) {
                ((BaseMvcLazyLoadFragment) child).lazyLoadData();
            }
        }
    }

    public void lazyLoadData() {
        if (mIsViewCreated && mIsVisibleToUser && isParentVisible() && !mIsDataLoaded) {
            loadData();
            mIsDataLoaded = true;
            //通知子Fragment请求数据
            dispatchParentVisibleState();
        }
    }

    protected abstract void loadData();

}
