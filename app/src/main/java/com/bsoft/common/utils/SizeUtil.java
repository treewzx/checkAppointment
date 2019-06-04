package com.bsoft.common.utils;

import android.content.Context;
import android.support.annotation.DimenRes;

import com.bsoft.checkappointment.MyApplication;

/**
 * Created by 盛启丰
 * Email : shengqf@bsoft.com.cn
 * date : 2018/4/25
 */
public class SizeUtil {
    private static Context mContext;

    public static void setContext(Context context) {
        mContext = context;
    }

    public static int dp2px(float dpValue) {
        final float scale =
                mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dp(float pxValue) {
        final float scale =
                mContext.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int sp2px(float spValue) {
        final float fontScale =
                mContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int px2sp(float pxValue) {
        final float fontScale =
                mContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }
    public static int getDpSize(@DimenRes int dimenRes){
        if(mContext!=null){
            return (int) mContext.getResources().getDimension(dimenRes);
        }
        return 0;
    }
    public static int getSpSize(@DimenRes int dimenRes){
        if(mContext!=null){
            return (int) mContext.getResources().getDimension(dimenRes);
        }
        return 0;
    }

}
