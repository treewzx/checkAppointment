package com.bsoft.common.utils;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/5/29.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
public class ToastUtil {
    private static Application app;

    private ToastUtil() {
    }

    public static void init(Application app) {
        ToastUtil.app = app;
    }

    public static void showShort(String msg) {
        if (app == null) return;
        showShort(app, msg);
    }

    public static void showLength(String msg) {
        if (app == null) return;
        showLength(app, msg);
    }

    public static void showShort(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }


    public static void showLength(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

}
