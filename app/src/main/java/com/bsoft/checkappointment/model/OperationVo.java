package com.bsoft.checkappointment.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/6/11.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
public class OperationVo implements Parcelable {

    private String operationId;
    private String patientCode;
    private String patientName;
    private String operationCode;
    private String operationName;
    private String departmentCode;
    private String departmentName;
    private int status;
    private String statusStr;
    private String operatingRoom;
    private String operativeTime;
    private String operativePlace;


    public OperationVo() {

    }

    protected OperationVo(Parcel in) {
        operationId = in.readString();
        patientCode = in.readString();
        patientName = in.readString();
        operationCode = in.readString();
        operationName = in.readString();
        departmentCode = in.readString();
        departmentName = in.readString();
        status = in.readInt();
        statusStr = in.readString();
        operatingRoom = in.readString();
        operativeTime = in.readString();
        operativePlace = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(operationId);
        dest.writeString(patientCode);
        dest.writeString(patientName);
        dest.writeString(operationCode);
        dest.writeString(operationName);
        dest.writeString(departmentCode);
        dest.writeString(departmentName);
        dest.writeInt(status);
        dest.writeString(statusStr);
        dest.writeString(operatingRoom);
        dest.writeString(operativeTime);
        dest.writeString(operativePlace);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OperationVo> CREATOR = new Creator<OperationVo>() {
        @Override
        public OperationVo createFromParcel(Parcel in) {
            return new OperationVo(in);
        }

        @Override
        public OperationVo[] newArray(int size) {
            return new OperationVo[size];
        }
    };

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public String getPatientCode() {
        return patientCode;
    }

    public void setPatientCode(String patientCode) {
        this.patientCode = patientCode;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getOperationCode() {
        return operationCode;
    }

    public void setOperationCode(String operationCode) {
        this.operationCode = operationCode;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public String getOperatingRoom() {
        return operatingRoom;
    }

    public void setOperatingRoom(String operatingRoom) {
        this.operatingRoom = operatingRoom;
    }

    public String getOperativeTime() {
        return operativeTime;
    }

    public void setOperativeTime(String operativeTime) {
        this.operativeTime = operativeTime;
    }

    public String getOperativePlace() {
        return operativePlace;
    }

    public void setOperativePlace(String operativePlace) {
        this.operativePlace = operativePlace;
    }
}
