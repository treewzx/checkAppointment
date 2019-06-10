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
public class ReAppointOrCancleCountVo implements Parcelable {

    private int updateTimes;


    protected ReAppointOrCancleCountVo(Parcel in) {
        updateTimes = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(updateTimes);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ReAppointOrCancleCountVo> CREATOR = new Creator<ReAppointOrCancleCountVo>() {
        @Override
        public ReAppointOrCancleCountVo createFromParcel(Parcel in) {
            return new ReAppointOrCancleCountVo(in);
        }

        @Override
        public ReAppointOrCancleCountVo[] newArray(int size) {
            return new ReAppointOrCancleCountVo[size];
        }
    };

    public int getUpdateTimes() {
        return updateTimes;
    }

    public void setUpdateTimes(int updateTimes) {
        this.updateTimes = updateTimes;
    }

    public ReAppointOrCancleCountVo() {

    }
}
