package com.bsoft.dischargemedication.expandable_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by shengqf
 * Email : shengqf@bsoft.com.cn
 * date : 2018/7/29
 * describe :  转自：https://www.jianshu.com/p/571f48f99332
 */
public abstract class BaseRecyclerViewAdapter<G, C, VH extends BaseViewHolder> extends RecyclerView.Adapter<VH> {

    public static final String TAG = BaseRecyclerViewAdapter.class.getSimpleName();

    private Context mContext;
    /**
     * all data
     */
    private List<RecyclerViewData<G, C>> allDatas;
    /**
     * showing datas
     */
    private List showingDataList = new ArrayList<>();

    /**
     * child datas
     */
    private List<List<C>> childList;

    public BaseRecyclerViewAdapter(Context context, List<RecyclerViewData<G, C>> list) {
        this.mContext = context;
        this.allDatas = list;
        setShowingData();
        this.notifyDataSetChanged();
    }

    public void setAllDatas(List<RecyclerViewData<G, C>> allDatas) {
        this.allDatas = allDatas;
        setShowingData();
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return null == showingDataList ? 0 : showingDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (showingDataList.get(position) instanceof GroupItem) {
            return BaseViewHolder.VIEW_TYPE_PARENT;
        } else {
            return BaseViewHolder.VIEW_TYPE_CHILD;
        }
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case BaseViewHolder.VIEW_TYPE_PARENT:
                view = getGroupView(parent);
                break;
            case BaseViewHolder.VIEW_TYPE_CHILD:
                view = getChildView(parent);
                break;
        }
        return createRealViewHolder(mContext, view, viewType);
    }


    @Override
    public void onBindViewHolder(@NonNull final VH holder, final int showDataPosition) {
        final Object item = showingDataList.get(showDataPosition);
        final int groupPosition = getGroupPosition(showDataPosition);
        final int childPosition = getChildPosition(groupPosition, showDataPosition);
        if (item != null && item instanceof GroupItem) {
            onBindGroupHolder(holder, groupPosition, showDataPosition, (G) ((GroupItem) item).getGroupData());
        } else {
            onBindChildHolder(holder, groupPosition, childPosition, showDataPosition, (C) item);
        }
    }

    /**
     * setup showing datas
     */
    private void setShowingData() {
        if (null != showingDataList) {
            showingDataList.clear();
        }
        if (this.childList == null) {
            this.childList = new ArrayList<>();
        }
        childList.clear();
        GroupItem<G, C> groupItem;
        for (int i = 0; i < allDatas.size(); i++) {
            if (allDatas.get(i).getGroupItem() != null) {
                groupItem = allDatas.get(i).getGroupItem();
            } else {
                break;
            }
            childList.add(i, groupItem.getChildList());
            showingDataList.add(groupItem);
            if (groupItem.hasChild() && groupItem.isExpand()) {
                showingDataList.addAll(groupItem.getChildList());
            }
        }
    }

    public void expandGroup(int showDataPosition) {
        Object item = showingDataList.get(showDataPosition);
        if (null == item) {
            return;
        }
        if (!(item instanceof GroupItem)) {
            return;
        }
        if (((GroupItem) item).isExpand()) {
            return;
        }
        if (!canExpandAll()) {
            for (int i = 0; i < showingDataList.size(); i++) {
                if (i != showDataPosition) {
                    int tempPosition = collapseGroup(i);
                    if (tempPosition != -1) {
                        showDataPosition = tempPosition;
                    }
                }
            }
        }

        List<BaseItem> tempChildList;
        if (((GroupItem) item).hasChild()) {
            tempChildList = ((GroupItem) item).getChildList();
            ((GroupItem) item).onExpand();
            if (canExpandAll()) {
                showingDataList.addAll(showDataPosition + 1, tempChildList);
                notifyItemRangeInserted(showDataPosition + 1, tempChildList.size());
                notifyItemRangeChanged(showDataPosition + 1, showingDataList.size() - (showDataPosition + 1));
            } else {
                int tempPsi = showingDataList.indexOf(item);
                showingDataList.addAll(tempPsi + 1, tempChildList);
                notifyItemRangeInserted(tempPsi + 1, tempChildList.size());
                notifyItemRangeChanged(tempPsi + 1, showingDataList.size() - (tempPsi + 1));
            }
        }
    }

    /**
     * collapseGroup
     *
     * @param position showingDatas position
     */
    public int collapseGroup(int position) {
        Object item = showingDataList.get(position);
        if (null == item) {
            return -1;
        }
        if (!(item instanceof GroupItem)) {
            return -1;
        }
        if (!((GroupItem) item).isExpand()) {
            return -1;
        }
        int tempSize = showingDataList.size();
        List<BaseItem> tempChildList;
        if (((GroupItem) item).hasChild()) {
            tempChildList = ((GroupItem) item).getChildList();
            ((GroupItem) item).onExpand();
            showingDataList.removeAll(tempChildList);
            notifyItemRangeRemoved(position + 1, tempChildList.size());
            notifyItemRangeChanged(position + 1, tempSize - (position + 1));
            return position;
        }
        return -1;
    }

    /**
     * @param position showingDatas position
     * @return GroupPosition
     */
    private int getGroupPosition(int position) {
        Object item = showingDataList.get(position);
        if (item instanceof GroupItem) {
            for (int j = 0; j < allDatas.size(); j++) {
                if (allDatas.get(j).getGroupItem().equals(item)) {
                    return j;
                }
            }
        }
        for (int i = 0; i < childList.size(); i++) {
            if (childList.get(i)!=null &&childList.get(i).contains(item)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * @param groupPosition
     * @param showDataPosition
     * @return ChildPosition
     */
    private int getChildPosition(int groupPosition, int showDataPosition) {
        Object item = showingDataList.get(showDataPosition);
        try {
            if(childList.get(groupPosition)!=null){
                return childList.get(groupPosition).indexOf(item);
            }
        } catch (IndexOutOfBoundsException ex) {
            Log.e(TAG, ex.getMessage());
        }
        return 0;
    }

    /**
     * return groupView
     */
    public abstract View getGroupView(ViewGroup parent);

    /**
     * return childView
     */
    public abstract View getChildView(ViewGroup parent);

    /**
     * return <VH extends BaseViewHolder> instance
     */
    public abstract VH createRealViewHolder(Context context, View view, int viewType);

    /**
     * onBind groupData to groupView
     *
     */
    public abstract void onBindGroupHolder(VH holder, int groupPosition, int showDataPosition, G groupVo);

    /**
     * onBind childData to childView
     *
     * @param holder
     * @param
     */
    public abstract void onBindChildHolder(VH holder, int groupPosition, int childPosition, int showDataPosition, C childVo);

    /**
     * if return true Allow all expand otherwise Only one can be expand at the same time
     */
    public boolean canExpandAll() {
        return true;
    }

    /**
     * 对原数据进行增加删除，调用此方法进行notify
     */
    public void notifyRecyclerViewData() {
        notifyDataSetChanged();
        setShowingData();
    }
}
