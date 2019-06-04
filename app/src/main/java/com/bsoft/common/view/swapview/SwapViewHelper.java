package com.bsoft.common.view.swapview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 用于切换布局,用一个新的布局替换掉原先的布局
 *
 * @author LuckyJayce
 */
public class SwapViewHelper implements ISwapViewHelper {
    private View mOriginView;
    private ViewGroup mParentView;
    private View mCurrentView;
    private ViewGroup.LayoutParams mLayoutParams;
    private int mViewIndex;

    public SwapViewHelper(View view) {
        super();
        mOriginView = view;
    }

    private void init() {
        mLayoutParams = mOriginView.getLayoutParams();
        if (mOriginView.getParent() != null) {
            mParentView = (ViewGroup) mOriginView.getParent();
        } else {
            mParentView = mOriginView.getRootView().findViewById(android.R.id.content);
        }
        int count = mParentView.getChildCount();
        for (int index = 0; index < count; index++) {
            if (mOriginView == mParentView.getChildAt(index)) {
                mViewIndex = index;
                break;
            }
        }
        mCurrentView = mOriginView;
    }

    @Override
    public View getCurrentLayout() {
        return mCurrentView;
    }

    @Override
    public void restoreView() {
        showLayout(mOriginView);
    }

    @Override
    public void showLayout(View view) {
        if (mParentView == null) {
            init();
        }
        this.mCurrentView = view;
        // 如果已经是那个view，那就不需要再进行替换操作了
        if (mParentView.getChildAt(mViewIndex) != view) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
            mParentView.getChildAt(mViewIndex).clearAnimation();
            mParentView.removeViewAt(mViewIndex);
            mParentView.addView(view, mViewIndex, mLayoutParams);
        }
    }

    @Override
    public void showLayout(int layoutId) {
        showLayout(inflate(layoutId));
    }

    @Override
    public View inflate(int layoutId) {
        return LayoutInflater.from(getContext()).inflate(layoutId, null);
    }

    @Override
    public Context getContext() {
        return mOriginView.getContext();
    }

    @Override
    public View getView() {
        return mOriginView;
    }
}
