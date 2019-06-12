package com.bsoft.checkappointment.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/5/31.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
public class AppointTimeVo implements Parcelable {

    /**
     * numberStartTime : 2019-05-31 09:00:00
     * numberEndTime : 2019-05-31 10:00:00
     * appointmentQueueCode : TestQueue01
     * appointmentQueueName : 测试队列01
     * defaultSign : true
     * totalNumberCount : 50
     * remainNumberCount : 48
     */
    private boolean isPreviousSelected;
    private String numberStartTime;
    private String numberEndTime;
    private String appointmentQueueCode;
    private String appointmentQueueName;
    private boolean defaultSign;
    private int totalNumberCount;
    private int remainNumberCount;
    private String checkAddress;

    public AppointTimeVo() {

    }

    protected AppointTimeVo(Parcel in) {
        isPreviousSelected = in.readByte() != 0;
        numberStartTime = in.readString();
        numberEndTime = in.readString();
        appointmentQueueCode = in.readString();
        appointmentQueueName = in.readString();
        defaultSign = in.readByte() != 0;
        totalNumberCount = in.readInt();
        remainNumberCount = in.readInt();
        checkAddress = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isPreviousSelected ? 1 : 0));
        dest.writeString(numberStartTime);
        dest.writeString(numberEndTime);
        dest.writeString(appointmentQueueCode);
        dest.writeString(appointmentQueueName);
        dest.writeByte((byte) (defaultSign ? 1 : 0));
        dest.writeInt(totalNumberCount);
        dest.writeInt(remainNumberCount);
        dest.writeString(checkAddress);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppointTimeVo> CREATOR = new Creator<AppointTimeVo>() {
        @Override
        public AppointTimeVo createFromParcel(Parcel in) {
            return new AppointTimeVo(in);
        }

        @Override
        public AppointTimeVo[] newArray(int size) {
            return new AppointTimeVo[size];
        }
    };

    public boolean isPreviousSelected() {
        return isPreviousSelected;
    }

    public void setPreviousSelected(boolean previousSelected) {
        isPreviousSelected = previousSelected;
    }

    public String getNumberStartTime() {
        return numberStartTime;
    }

    public void setNumberStartTime(String numberStartTime) {
        this.numberStartTime = numberStartTime;
    }

    public String getNumberEndTime() {
        return numberEndTime;
    }

    public void setNumberEndTime(String numberEndTime) {
        this.numberEndTime = numberEndTime;
    }

    public String getAppointmentQueueCode() {
        return appointmentQueueCode;
    }

    public void setAppointmentQueueCode(String appointmentQueueCode) {
        this.appointmentQueueCode = appointmentQueueCode;
    }

    public String getAppointmentQueueName() {
        return appointmentQueueName;
    }

    public void setAppointmentQueueName(String appointmentQueueName) {
        this.appointmentQueueName = appointmentQueueName;
    }

    public boolean isDefaultSign() {
        return defaultSign;
    }

    public void setDefaultSign(boolean defaultSign) {
        this.defaultSign = defaultSign;
    }

    public int getTotalNumberCount() {
        return totalNumberCount;
    }

    public void setTotalNumberCount(int totalNumberCount) {
        this.totalNumberCount = totalNumberCount;
    }

    public int getRemainNumberCount() {
        return remainNumberCount;
    }

    public void setRemainNumberCount(int remainNumberCount) {
        this.remainNumberCount = remainNumberCount;
    }

    public String getCheckAddress() {
        return checkAddress;
    }

    public void setCheckAddress(String checkAddress) {
        this.checkAddress = checkAddress;
    }



}
