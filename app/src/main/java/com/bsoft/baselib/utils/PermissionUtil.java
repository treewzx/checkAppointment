package com.bsoft.baselib.utils;

import android.Manifest;
import android.support.annotation.NonNull;
import android.util.Log;

import com.bsoft.baselib.http.exception.ApiException;
import com.bsoft.baselib.http.rxjava.BaseObserver;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/6/12.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
public class PermissionUtil {
    public static final String TAG = "Permission";

    private PermissionUtil() {
        throw new IllegalStateException("you can't instantiate me!");
    }

    public static void requestPermission(String permission, RxPermissions rxPermissions, final PermissionCallback permissionCallback) {
        requestPermissions(rxPermissions, permissionCallback, permission);
    }


    public static void requestPermissions(RxPermissions rxPermissions, final PermissionCallback permissionCallback, String... permissions) {
        if (permissions == null || permissions.length == 0) return;

        List<String> needRequest = new ArrayList<>();
        for (String permission : permissions) { //过滤调已经申请过的权限
            if (!rxPermissions.isGranted(permission)) {
                needRequest.add(permission);
            }
        }
        if (needRequest.isEmpty()) {//为空则都已经申请过，直接执行通过的回调
            permissionCallback.onPermissionGranted();
        } else {//不为空则需要申请
            rxPermissions
                    .requestEach(needRequest.toArray(new String[needRequest.size()]))
                    .buffer(permissions.length)
                    .subscribe(new BaseObserver<List<Permission>>() {
                        @Override
                        public void onNext(@NonNull List<Permission> permissions) {
                            List<String> failurePermissions = new ArrayList<>();
                            List<String> askNeverAgainPermissions = new ArrayList<>();
                            for (Permission p : permissions) {
                                if (!p.granted) {
                                    if (p.shouldShowRequestPermissionRationale) {
                                        failurePermissions.add(p.name);
                                    } else {
                                        askNeverAgainPermissions.add(p.name);
                                    }
                                }
                            }
                            if (failurePermissions.size() > 0) {
                                Log.d(TAG, "Request permissions failure");
                                permissionCallback.onPermissionDenied(failurePermissions);
                            }

                            if (askNeverAgainPermissions.size() > 0) {
                                Log.d(TAG, "Request permissions failure with ask never again");
                                permissionCallback.onPermissionDeniedWithAskNeverAgain(askNeverAgainPermissions);
                            }

                            if (failurePermissions.size() == 0 && askNeverAgainPermissions.size() == 0) {
                                Log.d(TAG, "Request permissions success");
                                permissionCallback.onPermissionGranted();
                            }
                        }

                        @Override
                        public void onError(ApiException exception) {

                        }
                    });
        }

    }

    /**
     * 请求摄像头权限
     */
    public static void launchCamera(RxPermissions rxPermissions, PermissionCallback permissionCallback) {
        requestPermissions(rxPermissions, permissionCallback, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA);
    }

    /**
     * 请求外部存储的权限
     */
    public static void externalStorage(RxPermissions rxPermissions, PermissionCallback permissionCallback) {
        requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, rxPermissions, permissionCallback);
    }

    /**
     * 请求发送短信权限
     */
    public static void sendSms(RxPermissions rxPermissions, PermissionCallback permissionCallback) {
        requestPermission(Manifest.permission.SEND_SMS, rxPermissions, permissionCallback);
    }

    /**
     * 请求打电话权限
     */
    public static void callPhone(RxPermissions rxPermissions, PermissionCallback permissionCallback) {
        requestPermission(Manifest.permission.CALL_PHONE, rxPermissions, permissionCallback);
    }

    /**
     * 请求获取手机状态的权限
     */
    public static void readPhonestate(RxPermissions rxPermissions, PermissionCallback permissionCallback) {
        requestPermission(Manifest.permission.READ_PHONE_STATE, rxPermissions, permissionCallback);
    }

    public interface PermissionCallback {

        /**
         * 权限请求成功
         */
        void onPermissionGranted();

        /**
         * 用户拒绝了权限请求, 权限请求失败, 但还可以继续请求该权限
         *
         * @param permissions 请求失败的权限名
         */
        void onPermissionDenied(List<String> permissions);

        /**
         * 用户拒绝了权限请求并且用户选择了以后不再询问, 权限请求失败, 这时将不能继续请求该权限, 需要提示用户进入设置页面打开该权限
         *
         * @param permissions 请求失败的权限名
         */
        void onPermissionDeniedWithAskNeverAgain(List<String> permissions);
    }
}
