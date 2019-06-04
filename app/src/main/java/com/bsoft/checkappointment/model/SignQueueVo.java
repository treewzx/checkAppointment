package com.bsoft.checkappointment.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/6/4.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
public class SignQueueVo implements Parcelable {

    /**
     * departmentName : 影像科
     * departmentLocation : 门诊3楼207
     * serialNumber : 7
     * currentNumber : 2
     * frontNumber : 4
     * avgTime : 1
     * expectTime : 2019-05-31 13:40:58
     * queuesUpdateTime : 2019-05-31 13:36:58
     * guidanceInformation : 门诊大楼(3号楼)电梯出口右转
     * tips : 请提前在科室外等候，避免过号
     */

    private String departmentName;
    private String departmentLocation;
    private String serialNumber;
    private String currentNumber;
    private int frontNumber;
    private int avgTime;
    private String expectTime;
    private String queuesUpdateTime;
    private String guidanceInformation;
    private String tips;

    public SignQueueVo(){

    }

    protected SignQueueVo(Parcel in) {
        departmentName = in.readString();
        departmentLocation = in.readString();
        serialNumber = in.readString();
        currentNumber = in.readString();
        frontNumber = in.readInt();
        avgTime = in.readInt();
        expectTime = in.readString();
        queuesUpdateTime = in.readString();
        guidanceInformation = in.readString();
        tips = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(departmentName);
        dest.writeString(departmentLocation);
        dest.writeString(serialNumber);
        dest.writeString(currentNumber);
        dest.writeInt(frontNumber);
        dest.writeInt(avgTime);
        dest.writeString(expectTime);
        dest.writeString(queuesUpdateTime);
        dest.writeString(guidanceInformation);
        dest.writeString(tips);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SignQueueVo> CREATOR = new Creator<SignQueueVo>() {
        @Override
        public SignQueueVo createFromParcel(Parcel in) {
            return new SignQueueVo(in);
        }

        @Override
        public SignQueueVo[] newArray(int size) {
            return new SignQueueVo[size];
        }
    };

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentLocation() {
        return departmentLocation;
    }

    public void setDepartmentLocation(String departmentLocation) {
        this.departmentLocation = departmentLocation;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getCurrentNumber() {
        return currentNumber;
    }

    public void setCurrentNumber(String currentNumber) {
        this.currentNumber = currentNumber;
    }

    public int getFrontNumber() {
        return frontNumber;
    }

    public void setFrontNumber(int frontNumber) {
        this.frontNumber = frontNumber;
    }

    public int getAvgTime() {
        return avgTime;
    }

    public void setAvgTime(int avgTime) {
        this.avgTime = avgTime;
    }

    public String getExpectTime() {
        return expectTime;
    }

    public void setExpectTime(String expectTime) {
        this.expectTime = expectTime;
    }

    public String getQueuesUpdateTime() {
        return queuesUpdateTime;
    }

    public void setQueuesUpdateTime(String queuesUpdateTime) {
        this.queuesUpdateTime = queuesUpdateTime;
    }

    public String getGuidanceInformation() {
        return guidanceInformation;
    }

    public void setGuidanceInformation(String guidanceInformation) {
        this.guidanceInformation = guidanceInformation;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }
}
