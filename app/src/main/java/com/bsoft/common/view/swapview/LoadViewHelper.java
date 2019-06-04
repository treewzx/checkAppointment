package com.bsoft.common.view.swapview;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.bsoft.checkappointment.R;
import com.bsoft.common.view.roundview.RoundTextView;


/**
 * 自定义要切换的布局，通过IVaryViewHelper实现真正的切换<br>
 * 使用者可以根据自己的需求，使用自己定义的布局样式
 */
public class LoadViewHelper {

    private ISwapViewHelper mHelper;
    private View.OnClickListener mClickListener;
    private SwipeRefreshLayout mRefreshLayout;

    public LoadViewHelper(View view) {
        this(new SwapViewHelper(view));
    }

    private LoadViewHelper(ISwapViewHelper helper) {
        super();
        this.mHelper = helper;
    }

    public void bindRefreshLayout(@NonNull SwipeRefreshLayout refreshLayout) {
        mRefreshLayout = refreshLayout;
    }

    public void unbindRefreshLayout() {
        mRefreshLayout = null;
    }

    /**
     * replace the origin view with a custom view
     */
    public void showCustomView(View view) {
        mHelper.showLayout(view);
    }

    /**
     * replace the origin view with a custom view
     */
    public void showCustomView(int layoutId) {
        mHelper.showLayout(layoutId);
    }


    /**
     * replace the origin view with a default error view
     */
    public void showError() {
        showError("");
    }

    /**
     * replace the origin view with a default error view
     */
    public void showError(@Nullable String errorText) {
        showError(errorText, "点击刷新");
    }
    /**
     * replace the origin view with a default error view
     */
    public void showError(@Nullable View.OnClickListener retryClickListener) {
        showError(null, "点击刷新", retryClickListener);
    }

    /**
     * replace the origin view with a default error view
     */
    public void showError(@Nullable String errorText, @Nullable String errorBtnText) {
        showError(errorText, errorBtnText, mClickListener);
    }

    /**
     * replace the origin view with a default error view
     */
    public void showError(@Nullable String errorText, @Nullable String errorBtnText,
                          @Nullable View.OnClickListener retryClickListener) {
        mClickListener = retryClickListener;

        View errorLayout = mHelper.inflate(R.layout.base_view_load_error);
        RoundTextView retryBtn = errorLayout.findViewById(R.id.refresh_tv);
        TextView msgTv = errorLayout.findViewById(R.id.msg_tv);
        if (TextUtils.isEmpty(errorText)) {
            msgTv.setText("加载失败");
        } else {
            msgTv.setText(errorText);
        }
        if (TextUtils.isEmpty(errorBtnText) || mClickListener == null) {
            retryBtn.setVisibility(View.GONE);
        } else {
            retryBtn.setVisibility(View.VISIBLE);
            retryBtn.setText(errorBtnText);
        }
        retryBtn.setOnClickListener(mClickListener);

        mHelper.showLayout(errorLayout);
    }

    public void showNoNetwork(){
        View emptyLayout = mHelper.inflate(R.layout.base_view_load_empty);
        TextView msgTv = emptyLayout.findViewById(R.id.msg_tv);
        ImageView emptyIv = emptyLayout.findViewById(R.id.empty_iv);
        if (msgTv != null) {
            msgTv.setText("无网络");
        }
        if (emptyIv != null) {
            emptyIv.setImageResource(R.drawable.base_bg_no_net);
        }
        mHelper.showLayout(emptyLayout);
    }

    public void showEmpty(){
        showEmpty("暂无相关数据", 0);
    }

    public void showEmpty(String emptyText, int res) {
        View emptyLayout = mHelper.inflate(R.layout.base_view_load_empty);
        TextView msgTv = emptyLayout.findViewById(R.id.msg_tv);
        ImageView emptyIv = emptyLayout.findViewById(R.id.empty_iv);
        if (msgTv != null && !TextUtils.isEmpty(emptyText)) {
            msgTv.setText(emptyText);
        }
        if (emptyIv != null && res != 0) {
            emptyIv.setImageResource(res);
        }
        mHelper.showLayout(emptyLayout);
    }

    public void showEmpty(SwipeRefreshLayout.OnRefreshListener listener,String emptyText) {
        showEmpty(emptyText, 0,listener);
    }

    public void showEmpty(SwipeRefreshLayout.OnRefreshListener listener) {
        showEmpty("暂无相关数据", 0,listener);
    }

    public void showEmpty(String emptyText, SwipeRefreshLayout.OnRefreshListener listener) {
        showEmpty(emptyText, 0,listener);
    }

    public void showEmpty(int res,SwipeRefreshLayout.OnRefreshListener listener) {
        showEmpty("暂无相关数据",  res,listener);
    }

    public void showEmpty(String emptyText, int res, SwipeRefreshLayout.OnRefreshListener listener) {
        View emptyLayout = mHelper.inflate(R.layout.base_view_load_empty_refresh);
        TextView msgTv = emptyLayout.findViewById(R.id.msg_tv);
        ImageView emptyIv = emptyLayout.findViewById(R.id.empty_iv);
        if (msgTv != null && !TextUtils.isEmpty(emptyText)) {
            msgTv.setText(emptyText);
        }
        if (emptyIv != null && res != 0) {
            emptyIv.setImageResource(res);
        }
        SwipeRefreshLayout refreshLayout = emptyLayout.findViewById(R.id.swipeRefreshLayout);
        refreshLayout.setColorSchemeResources(R.color.main);
        refreshLayout.setOnRefreshListener(listener);
        mHelper.showLayout(emptyLayout);
    }


    /**
     * only used for RefreshLayout
     */
    public void startRefreshing() {
        if (mRefreshLayout != null) {
            mRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    mRefreshLayout.setRefreshing(true);
                }
            });
        }
    }

    /**
     * only used for RefreshLayout
     */
    public void stopRefreshing() {
        if (mRefreshLayout != null) {
            mRefreshLayout.setRefreshing(false);
//            mRefreshLayout.post(new Runnable() {
//                @Override
//                public void run() {
//                    mRefreshLayout.setRefreshing(false);
//                }
//            });
        }
    }

    /**
     * show the origin view
     */
    public void restore() {
        mHelper.restoreView();
    }

    public void setOnRetryListener(@Nullable View.OnClickListener clickListener) {
        this.mClickListener = clickListener;
    }

    private static Animation getRotateAnimation() {
        final RotateAnimation rotateAnimation = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF,
                .5f);
        rotateAnimation.setDuration(1500);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        return rotateAnimation;
    }
}
