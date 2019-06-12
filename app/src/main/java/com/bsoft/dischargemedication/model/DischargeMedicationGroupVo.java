package com.bsoft.dischargemedication.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/6/12.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
public class DischargeMedicationGroupVo implements Parcelable {


    /**
     * hospitalName : 创业试点医院
     * inHospitalRecordNumber : A01232
     * inHospitalRecordCode : 0234123
     * patientName : 测试1
     * inDate : 2019-05-22 10:23:23
     * outDate : 2019-05-22 10:23:23
     * patientSexText : 男
     * patientAge : 23
     * departmentName : 外分泌科
     * bedNumber : A023
     * dischargeDiagnosis : 外分泌失调
     * detailsItems : [{"itemTypeCode":"1","itemTypeName":"西药处方","itemName":"阿莫西林胶囊","itemNumber":"1","price":"23.05","amount":"23.05","unit":"盒","specifications":"0.25g*24粒*2板","drugFrequency":"3次/日","drugConsumption":"2片/次","drugRoute":"口服","itemRemark":""},{"itemTypeCode":"1","itemTypeName":"西药处方","itemName":"银翘片","itemNumber":"2","price":"20","amount":"40","unit":"盒","specifications":"18片*2袋","drugFrequency":"3次/日","drugConsumption":"2片/次","drugRoute":"口服","itemRemark":""},{"itemTypeCode":"2","itemTypeName":"中药处方","itemName":"不知名中成药1","itemNumber":"3","price":"20","amount":"60","unit":"盒","specifications":"18片*2袋","drugFrequency":"3次/日","drugConsumption":"2片/次","drugRoute":"口服","itemRemark":""},{"itemTypeCode":"2","itemTypeName":"中药处方","itemName":"不知名中成药2","itemNumber":"3","price":"20","amount":"60","unit":"盒","specifications":"18片*2袋","drugFrequency":"3次/日","drugConsumption":"2片/次","drugRoute":"口服","itemRemark":""},{"itemTypeCode":"3","itemTypeName":"草药处方","itemName":"人参4g    卜芥4g    丁香4g   刀豆4g    干姜4g    广白4g   大蓟4g    山丹4g","itemNumber":"共五剂","price":"10","amount":"50","unit":"帖","specifications":"g","drugFrequency":"1次/日","drugConsumption":"1剂/次","drugRoute":"煎服","itemRemark":""}]
     */



    /**
     * hospitalName : 创业试点医院
     * inHospitalRecordNumber : A01232
     * inHospitalRecordCode : 0234123
     * patientName : 测试1
     * inDate : 2019-05-22 10:23:23
     * outDate : 2019-05-22 10:23:23
     * patientSexText : 男
     * patientAge : 23
     * departmentName : 外分泌科
     * bedNumber : A023
     * dischargeDiagnosis : 外分泌失调
     * detailsItems : [{"itemTypeCode":"1","itemTypeName":"西药处方","itemName":"阿莫西林胶囊","itemNumber":"1","price":"23.05","amount":"23.05","unit":"盒","specifications":"0.25g*24粒*2板","drugFrequency":"3次/日","drugConsumption":"2片/次","drugRoute":"口服","itemRemark":""},{"itemTypeCode":"1","itemTypeName":"西药处方","itemName":"银翘片","itemNumber":"2","price":"20","amount":"40","unit":"盒","specifications":"18片*2袋","drugFrequency":"3次/日","drugConsumption":"2片/次","drugRoute":"口服","itemRemark":""},{"itemTypeCode":"2","itemTypeName":"中药处方","itemName":"不知名中成药1","itemNumber":"3","price":"20","amount":"60","unit":"盒","specifications":"18片*2袋","drugFrequency":"3次/日","drugConsumption":"2片/次","drugRoute":"口服","itemRemark":""},{"itemTypeCode":"2","itemTypeName":"中药处方","itemName":"不知名中成药2","itemNumber":"3","price":"20","amount":"60","unit":"盒","specifications":"18片*2袋","drugFrequency":"3次/日","drugConsumption":"2片/次","drugRoute":"口服","itemRemark":""},{"itemTypeCode":"3","itemTypeName":"草药处方","itemName":"人参4g    卜芥4g    丁香4g   刀豆4g    干姜4g    广白4g   大蓟4g    山丹4g","itemNumber":"共五剂","price":"10","amount":"50","unit":"帖","specifications":"g","drugFrequency":"1次/日","drugConsumption":"1剂/次","drugRoute":"煎服","itemRemark":""}]
     */
    public boolean isExpanded;
    private String hospitalName;
    private String inHospitalRecordNumber;
    private String inHospitalRecordCode;
    private String patientName;
    private String inDate;
    private String outDate;
    private String patientSexText;
    private String patientAge;
    private String departmentName;
    private String bedNumber;
    private String dischargeDiagnosis;
    private List<DischargeMedicationChildVo> detailsItems;

    public DischargeMedicationGroupVo(){

    }

    protected DischargeMedicationGroupVo(Parcel in) {
        isExpanded = in.readByte() != 0;
        hospitalName = in.readString();
        inHospitalRecordNumber = in.readString();
        inHospitalRecordCode = in.readString();
        patientName = in.readString();
        inDate = in.readString();
        outDate = in.readString();
        patientSexText = in.readString();
        patientAge = in.readString();
        departmentName = in.readString();
        bedNumber = in.readString();
        dischargeDiagnosis = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isExpanded ? 1 : 0));
        dest.writeString(hospitalName);
        dest.writeString(inHospitalRecordNumber);
        dest.writeString(inHospitalRecordCode);
        dest.writeString(patientName);
        dest.writeString(inDate);
        dest.writeString(outDate);
        dest.writeString(patientSexText);
        dest.writeString(patientAge);
        dest.writeString(departmentName);
        dest.writeString(bedNumber);
        dest.writeString(dischargeDiagnosis);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DischargeMedicationGroupVo> CREATOR = new Creator<DischargeMedicationGroupVo>() {
        @Override
        public DischargeMedicationGroupVo createFromParcel(Parcel in) {
            return new DischargeMedicationGroupVo(in);
        }

        @Override
        public DischargeMedicationGroupVo[] newArray(int size) {
            return new DischargeMedicationGroupVo[size];
        }
    };

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getInHospitalRecordNumber() {
        return inHospitalRecordNumber;
    }

    public void setInHospitalRecordNumber(String inHospitalRecordNumber) {
        this.inHospitalRecordNumber = inHospitalRecordNumber;
    }

    public String getInHospitalRecordCode() {
        return inHospitalRecordCode;
    }

    public void setInHospitalRecordCode(String inHospitalRecordCode) {
        this.inHospitalRecordCode = inHospitalRecordCode;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getInDate() {
        return inDate;
    }

    public void setInDate(String inDate) {
        this.inDate = inDate;
    }

    public String getOutDate() {
        return outDate;
    }

    public void setOutDate(String outDate) {
        this.outDate = outDate;
    }

    public String getPatientSexText() {
        return patientSexText;
    }

    public void setPatientSexText(String patientSexText) {
        this.patientSexText = patientSexText;
    }

    public String getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(String patientAge) {
        this.patientAge = patientAge;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(String bedNumber) {
        this.bedNumber = bedNumber;
    }

    public String getDischargeDiagnosis() {
        return dischargeDiagnosis;
    }

    public void setDischargeDiagnosis(String dischargeDiagnosis) {
        this.dischargeDiagnosis = dischargeDiagnosis;
    }

    public List<DischargeMedicationChildVo> getDetailsItems() {
        return detailsItems;
    }

    public void setDetailsItems(List<DischargeMedicationChildVo> detailsItems) {
        this.detailsItems = detailsItems;
    }




}
