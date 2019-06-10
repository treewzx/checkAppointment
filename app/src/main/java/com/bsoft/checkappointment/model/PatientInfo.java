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
public class PatientInfo implements Parcelable {

    private String patientName;
    private int patientIdCardType;
    private String patientIdCardNumber;

    public PatientInfo() {

    }

    protected PatientInfo(Parcel in) {
        patientName = in.readString();
        patientIdCardType = in.readInt();
        patientIdCardNumber = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(patientName);
        dest.writeInt(patientIdCardType);
        dest.writeString(patientIdCardNumber);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PatientInfo> CREATOR = new Creator<PatientInfo>() {
        @Override
        public PatientInfo createFromParcel(Parcel in) {
            return new PatientInfo(in);
        }

        @Override
        public PatientInfo[] newArray(int size) {
            return new PatientInfo[size];
        }
    };

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public int getPatientIdCardType() {
        return patientIdCardType;
    }

    public void setPatientIdCardType(int patientIdCardType) {
        this.patientIdCardType = patientIdCardType;
    }

    public String getPatientIdCardNumber() {
        return patientIdCardNumber;
    }

    public void setPatientIdCardNumber(String patientIdCardNumber) {
        this.patientIdCardNumber = patientIdCardNumber;

    }

}
