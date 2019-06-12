package com.bsoft.checkappointment.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/6/3.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
public class PatientAppointmentVo implements Parcelable {


    /**
     * patientName : 测试001
     * patientAge : 23个月
     * patientType : 3
     * patientNumber : A0101
     * checkRequestNumber : NO1560125349925R95533
     * checkItemCode : MRI
     * checkItemName : MRI
     * appointmentSign : 1
     * signInSign : 0
     * executionSign : 0
     * appointmentRecordId : 1560125349924R18492
     * appointmentDateTime : 2019-06-10 04:09:09
     * automaticCancellationTime : 2019-06-10 04:39:09
     * automaticCancellationRemainingSeconds : 1800
     * checkStartTime : 2019-06-10 10:09:09
     * checkEndTime : 2019-06-10 11:09:09
     * appointmentHospitalCode : A00001
     * appointmentHospitalName : 创业测试医院
     * appointmentDepartmentCode : B029
     * appointmentDepartmentName : 医院测试科室
     * appointmentQueueCode : Test01
     * appointmentQueueName : 测试队列01
     * appointmentSequenceNumber : 023
     * checkAddress : 门诊3楼210室
     * mattersNeedingAttention : 至少提前5分钟到检查地点
     * emptyStomach : true
     * holdBackUrine : false
     * emergencySign : 0
     * feeStatus : 0
     * examCode : 002
     * printCount : 0
     * bedNumber : BEDNOA001
     */

    private String patientName;
    private String patientAge;
    private int patientType;
    private String patientNumber;
    private String checkRequestNumber;
    private String checkItemCode;
    private String checkItemName;
    private int appointmentSign;
    private int signInSign;
    private int executionSign;
    private String appointmentRecordId;
    private String appointmentDateTime;
    private String automaticCancellationTime;
    private long automaticCancellationRemainingSeconds;
    private String checkStartTime;
    private String checkEndTime;
    private String appointmentHospitalCode;
    private String appointmentHospitalName;
    private String appointmentDepartmentCode;
    private String appointmentDepartmentName;
    private String appointmentQueueCode;
    private String appointmentQueueName;
    private String appointmentSequenceNumber;
    private String checkAddress;
    private String mattersNeedingAttention;
    private boolean emptyStomach;
    private boolean holdBackUrine;
    private int emergencySign;
    private int feeStatus;
    private String examCode;
    private int printCount;
    private String bedNumber;

    protected PatientAppointmentVo(Parcel in) {
        patientName = in.readString();
        patientAge = in.readString();
        patientType = in.readInt();
        patientNumber = in.readString();
        checkRequestNumber = in.readString();
        checkItemCode = in.readString();
        checkItemName = in.readString();
        appointmentSign = in.readInt();
        signInSign = in.readInt();
        executionSign = in.readInt();
        appointmentRecordId = in.readString();
        appointmentDateTime = in.readString();
        automaticCancellationTime = in.readString();
        automaticCancellationRemainingSeconds = in.readLong();
        checkStartTime = in.readString();
        checkEndTime = in.readString();
        appointmentHospitalCode = in.readString();
        appointmentHospitalName = in.readString();
        appointmentDepartmentCode = in.readString();
        appointmentDepartmentName = in.readString();
        appointmentQueueCode = in.readString();
        appointmentQueueName = in.readString();
        appointmentSequenceNumber = in.readString();
        checkAddress = in.readString();
        mattersNeedingAttention = in.readString();
        emptyStomach = in.readByte() != 0;
        holdBackUrine = in.readByte() != 0;
        emergencySign = in.readInt();
        feeStatus = in.readInt();
        examCode = in.readString();
        printCount = in.readInt();
        bedNumber = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(patientName);
        dest.writeString(patientAge);
        dest.writeInt(patientType);
        dest.writeString(patientNumber);
        dest.writeString(checkRequestNumber);
        dest.writeString(checkItemCode);
        dest.writeString(checkItemName);
        dest.writeInt(appointmentSign);
        dest.writeInt(signInSign);
        dest.writeInt(executionSign);
        dest.writeString(appointmentRecordId);
        dest.writeString(appointmentDateTime);
        dest.writeString(automaticCancellationTime);
        dest.writeLong(automaticCancellationRemainingSeconds);
        dest.writeString(checkStartTime);
        dest.writeString(checkEndTime);
        dest.writeString(appointmentHospitalCode);
        dest.writeString(appointmentHospitalName);
        dest.writeString(appointmentDepartmentCode);
        dest.writeString(appointmentDepartmentName);
        dest.writeString(appointmentQueueCode);
        dest.writeString(appointmentQueueName);
        dest.writeString(appointmentSequenceNumber);
        dest.writeString(checkAddress);
        dest.writeString(mattersNeedingAttention);
        dest.writeByte((byte) (emptyStomach ? 1 : 0));
        dest.writeByte((byte) (holdBackUrine ? 1 : 0));
        dest.writeInt(emergencySign);
        dest.writeInt(feeStatus);
        dest.writeString(examCode);
        dest.writeInt(printCount);
        dest.writeString(bedNumber);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PatientAppointmentVo> CREATOR = new Creator<PatientAppointmentVo>() {
        @Override
        public PatientAppointmentVo createFromParcel(Parcel in) {
            return new PatientAppointmentVo(in);
        }

        @Override
        public PatientAppointmentVo[] newArray(int size) {
            return new PatientAppointmentVo[size];
        }
    };

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(String patientAge) {
        this.patientAge = patientAge;
    }

    public int getPatientType() {
        return patientType;
    }

    public void setPatientType(int patientType) {
        this.patientType = patientType;
    }

    public String getPatientNumber() {
        return patientNumber;
    }

    public void setPatientNumber(String patientNumber) {
        this.patientNumber = patientNumber;
    }

    public String getCheckRequestNumber() {
        return checkRequestNumber;
    }

    public void setCheckRequestNumber(String checkRequestNumber) {
        this.checkRequestNumber = checkRequestNumber;
    }

    public String getCheckItemCode() {
        return checkItemCode;
    }

    public void setCheckItemCode(String checkItemCode) {
        this.checkItemCode = checkItemCode;
    }

    public String getCheckItemName() {
        return checkItemName;
    }

    public void setCheckItemName(String checkItemName) {
        this.checkItemName = checkItemName;
    }

    public int getAppointmentSign() {
        return appointmentSign;
    }

    public void setAppointmentSign(int appointmentSign) {
        this.appointmentSign = appointmentSign;
    }

    public int getSignInSign() {
        return signInSign;
    }

    public void setSignInSign(int signInSign) {
        this.signInSign = signInSign;
    }

    public int getExecutionSign() {
        return executionSign;
    }

    public void setExecutionSign(int executionSign) {
        this.executionSign = executionSign;
    }

    public String getAppointmentRecordId() {
        return appointmentRecordId;
    }

    public void setAppointmentRecordId(String appointmentRecordId) {
        this.appointmentRecordId = appointmentRecordId;
    }

    public String getAppointmentDateTime() {
        return appointmentDateTime;
    }

    public void setAppointmentDateTime(String appointmentDateTime) {
        this.appointmentDateTime = appointmentDateTime;
    }

    public String getAutomaticCancellationTime() {
        return automaticCancellationTime;
    }

    public void setAutomaticCancellationTime(String automaticCancellationTime) {
        this.automaticCancellationTime = automaticCancellationTime;
    }

    public long getAutomaticCancellationRemainingSeconds() {
        return automaticCancellationRemainingSeconds;
    }

    public void setAutomaticCancellationRemainingSeconds(long automaticCancellationRemainingSeconds) {
        this.automaticCancellationRemainingSeconds = automaticCancellationRemainingSeconds;
    }

    public String getCheckStartTime() {
        return checkStartTime;
    }

    public void setCheckStartTime(String checkStartTime) {
        this.checkStartTime = checkStartTime;
    }

    public String getCheckEndTime() {
        return checkEndTime;
    }

    public void setCheckEndTime(String checkEndTime) {
        this.checkEndTime = checkEndTime;
    }

    public String getAppointmentHospitalCode() {
        return appointmentHospitalCode;
    }

    public void setAppointmentHospitalCode(String appointmentHospitalCode) {
        this.appointmentHospitalCode = appointmentHospitalCode;
    }

    public String getAppointmentHospitalName() {
        return appointmentHospitalName;
    }

    public void setAppointmentHospitalName(String appointmentHospitalName) {
        this.appointmentHospitalName = appointmentHospitalName;
    }

    public String getAppointmentDepartmentCode() {
        return appointmentDepartmentCode;
    }

    public void setAppointmentDepartmentCode(String appointmentDepartmentCode) {
        this.appointmentDepartmentCode = appointmentDepartmentCode;
    }

    public String getAppointmentDepartmentName() {
        return appointmentDepartmentName;
    }

    public void setAppointmentDepartmentName(String appointmentDepartmentName) {
        this.appointmentDepartmentName = appointmentDepartmentName;
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

    public String getAppointmentSequenceNumber() {
        return appointmentSequenceNumber;
    }

    public void setAppointmentSequenceNumber(String appointmentSequenceNumber) {
        this.appointmentSequenceNumber = appointmentSequenceNumber;
    }

    public String getCheckAddress() {
        return checkAddress;
    }

    public void setCheckAddress(String checkAddress) {
        this.checkAddress = checkAddress;
    }

    public String getMattersNeedingAttention() {
        return mattersNeedingAttention;
    }

    public void setMattersNeedingAttention(String mattersNeedingAttention) {
        this.mattersNeedingAttention = mattersNeedingAttention;
    }

    public boolean isEmptyStomach() {
        return emptyStomach;
    }

    public void setEmptyStomach(boolean emptyStomach) {
        this.emptyStomach = emptyStomach;
    }

    public boolean isHoldBackUrine() {
        return holdBackUrine;
    }

    public void setHoldBackUrine(boolean holdBackUrine) {
        this.holdBackUrine = holdBackUrine;
    }

    public int getEmergencySign() {
        return emergencySign;
    }

    public void setEmergencySign(int emergencySign) {
        this.emergencySign = emergencySign;
    }

    public int getFeeStatus() {
        return feeStatus;
    }

    public void setFeeStatus(int feeStatus) {
        this.feeStatus = feeStatus;
    }

    public String getExamCode() {
        return examCode;
    }

    public void setExamCode(String examCode) {
        this.examCode = examCode;
    }

    public int getPrintCount() {
        return printCount;
    }

    public void setPrintCount(int printCount) {
        this.printCount = printCount;
    }

    public String getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(String bedNumber) {
        this.bedNumber = bedNumber;
    }



    public PatientAppointmentVo() {

    }

}
