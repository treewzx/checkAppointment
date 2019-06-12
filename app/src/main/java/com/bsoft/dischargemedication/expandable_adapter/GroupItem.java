package com.bsoft.dischargemedication.expandable_adapter;

import java.util.List;

/**
 * Created by shengqf
 * Email : shengqf@bsoft.com.cn
 * date : 2018/7/29
 * describe :  转自：https://www.jianshu.com/p/571f48f99332
 */
public class GroupItem<G,C> extends BaseItem{

    /**groupVo data*/
    private G groupVo;

    /** childList*/
    private List<C> childList;

    /** 是否展开,  默认展开*/
    private boolean isExpand = true;


    /** 返回是否是父节点*/
    @Override
    public boolean isParent() {
        return true;
    }

    @Override
    public boolean isExpand() {
        return isExpand;
    }


    public void onExpand() {
        isExpand = !isExpand;
    }

    public GroupItem(G groupVo, List<C> childList, boolean isExpand) {
        this.groupVo = groupVo;
        this.childList = childList;
        this.isExpand = isExpand;
    }

    public boolean hasChild(){
        if(getChildList() == null || getChildList().isEmpty() ){
            return false;
        }
        return true;
    }

    public List<C> getChildList() {
        return childList;
    }

    public void setChildList(List<C> childList) {
        this.childList = childList;
    }

    public void removeChild(int childPosition){

    }

    public G getGroupData() {
        return groupVo;
    }
}
