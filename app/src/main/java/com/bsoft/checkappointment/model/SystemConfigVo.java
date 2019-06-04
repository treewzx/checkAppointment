package com.bsoft.checkappointment.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;

import java.util.jar.Pack200;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/6/3.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
public class SystemConfigVo implements Parcelable {
    /*
    parameterKey:系统参数key string
    parameterValue:系统参数值 string
    parameterRemake:系统参数说明 string
    parameterUnit:单位 string
    */

    private String parameterKey;
    private String parameterValue;
    private String parameterRemake;
    private String parameterUnit;

    public SystemConfigVo() {

    }

    protected SystemConfigVo(Parcel in) {
        parameterKey = in.readString();
        parameterValue = in.readString();
        parameterRemake = in.readString();
        parameterUnit = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(parameterKey);
        dest.writeString(parameterValue);
        dest.writeString(parameterRemake);
        dest.writeString(parameterUnit);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SystemConfigVo> CREATOR = new Creator<SystemConfigVo>() {
        @Override
        public SystemConfigVo createFromParcel(Parcel in) {
            return new SystemConfigVo(in);
        }

        @Override
        public SystemConfigVo[] newArray(int size) {
            return new SystemConfigVo[size];
        }
    };

    public String getParameterKey() {
        return parameterKey;
    }

    public void setParameterKey(String parameterKey) {
        this.parameterKey = parameterKey;
    }

    public String getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(String parameterValue) {
        this.parameterValue = parameterValue;
    }

    public String getParameterRemake() {
        return parameterRemake;
    }

    public void setParameterRemake(String parameterRemake) {
        this.parameterRemake = parameterRemake;
    }

    public String getParameterUnit() {
        return parameterUnit;
    }

    public void setParameterUnit(String parameterUnit) {
        this.parameterUnit = parameterUnit;
    }
}
