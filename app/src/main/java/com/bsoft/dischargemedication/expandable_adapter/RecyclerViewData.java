package com.bsoft.dischargemedication.expandable_adapter;

import java.util.List;


/**
 * Created by shengqf
 * Email : shengqf@bsoft.com.cn
 * date : 2018/7/29
 * describe : 转自：https://www.jianshu.com/p/571f48f99332
 */
public class RecyclerViewData<G,C> {

    private GroupItem<G,C> groupItem;

    public RecyclerViewData(G groupVo, List<C> childList) {
        this.groupItem = new GroupItem<>(groupVo,childList,false);
    }

    public RecyclerViewData(G groupVo, List<C> childList, boolean isExpand) {
        this.groupItem = new GroupItem<>(groupVo,childList,isExpand);
    }

    public GroupItem<G,C> getGroupItem() {
        return groupItem;
    }

    public void setGroupItem(GroupItem<G,C> groupItem) {
        this.groupItem = groupItem;
    }

    public G getGroupData(){
        return groupItem.getGroupData();
    }

    public void removeChild(int position){
        if(null == groupItem || !groupItem.hasChild()){
            return;
        }
        groupItem.getChildList().remove(position);
    }

    public C getChild(int childPosition){
        return groupItem.getChildList().get(childPosition);
    }

}
