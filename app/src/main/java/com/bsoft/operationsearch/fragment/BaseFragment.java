package com.bsoft.operationsearch.fragment;

import com.bsoft.common.fragment.BaseLazyLoadFragment;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/6/11.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
public abstract class BaseFragment extends BaseLazyLoadFragment {

    @Override
    public void lazyLoadData() {
        loadData();
    }
}
