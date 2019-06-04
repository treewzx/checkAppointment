package com.bsoft.baselib.framework.callback;

import android.os.Bundle;
import android.support.annotation.Nullable;


/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/2/27.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
public interface ViewCallback {

    /**
     * 获取activity的页面布局view,返回是layout文件对应的R文件中的值，用于setContentView（int）
     *
     * @param savedInstanceState
     * @return
     */
    int getContentViewId(@Nullable Bundle savedInstanceState);


    /**
     * 初始化数据
     *
     * @param savedInstanceState
     */
    void initData(@Nullable Bundle savedInstanceState);

    /**
     * 是否使用EventBus
     *
     * @return
     */
    boolean useEventBus();

    /**
     * 是否使用ButterKnife
     */
    boolean useButterKnife();




}
