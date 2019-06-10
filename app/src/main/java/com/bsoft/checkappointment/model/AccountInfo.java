package com.bsoft.checkappointment.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/6/6.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
public class AccountInfo implements Parcelable {


    private String token;
    private String sn;
    private String hospitalCode;

    public AccountInfo() {

    }

    protected AccountInfo(Parcel in) {
        token = in.readString();
        sn = in.readString();
        hospitalCode = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(token);
        dest.writeString(sn);
        dest.writeString(hospitalCode);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AccountInfo> CREATOR = new Creator<AccountInfo>() {
        @Override
        public AccountInfo createFromParcel(Parcel in) {
            return new AccountInfo(in);
        }

        @Override
        public AccountInfo[] newArray(int size) {
            return new AccountInfo[size];
        }
    };

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getHospitalCode() {
        return hospitalCode;
    }

    public void setHospitalCode(String hospitalCode) {
        this.hospitalCode = hospitalCode;
    }
}
