package com.bsoft.dischargemedication.expandable_adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by shengqf
 * Email : shengqf@bsoft.com.cn
 * date : 2018/7/29
 * describe : 转自：https://www.jianshu.com/p/571f48f99332
 */
public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    public static final int VIEW_TYPE_PARENT = 1;
    public static final int VIEW_TYPE_CHILD = 2;

    public ViewGroup childView;

    public ViewGroup groupView;

    public BaseViewHolder(Context context, View itemView, int viewType) {
        super(itemView);
        switch (viewType) {
            case VIEW_TYPE_PARENT:
                groupView = (ViewGroup) itemView.findViewById(getGroupViewResId());
                break;
            case VIEW_TYPE_CHILD:
                childView = (ViewGroup) itemView.findViewById(getChildViewResId());
                break;
        }
    }

    /**
     * return ChildView root layout id
     */
    public abstract int getChildViewResId();

    /**
     * return GroupView root layout id
     */
    public abstract int getGroupViewResId();


}
