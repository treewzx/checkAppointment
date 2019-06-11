package com.bsoft.checkappointment.model;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/6/11.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
public class OperationDetailVo {


    /**
     * operationId : 100000000001
     * patientCode : 101
     * patientName : 王某某
     * operationCode : 001
     * operationName : 阑尾切除术
     * departmentCode : 1001
     * departmentName : 外科
     * status : 6
     * statusStr : 手术中
     * operatingRoom : 4号手术间
     * operativeTime : 2019-07-22 11:00:00
     * operativePlace : 1号楼4楼大厅4001
     * tableNumber : 1
     * anestheticMethods : 全身麻醉
     * surgeon : 王一通，王二通
     * anesthesiologist : 王三通，王四通
     * nurse : 王五通，王六通
     * considerations : null
     */

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
    private String tableNumber;
    private String anestheticMethods;
    private String surgeon;
    private String anesthesiologist;
    private String nurse;
    private String considerations;

    public OperationDetailVo() {

    }

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

    public String getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
    }

    public String getAnestheticMethods() {
        return anestheticMethods;
    }

    public void setAnestheticMethods(String anestheticMethods) {
        this.anestheticMethods = anestheticMethods;
    }

    public String getSurgeon() {
        return surgeon;
    }

    public void setSurgeon(String surgeon) {
        this.surgeon = surgeon;
    }

    public String getAnesthesiologist() {
        return anesthesiologist;
    }

    public void setAnesthesiologist(String anesthesiologist) {
        this.anesthesiologist = anesthesiologist;
    }

    public String getNurse() {
        return nurse;
    }

    public void setNurse(String nurse) {
        this.nurse = nurse;
    }

    public String getConsiderations() {
        return considerations;
    }

    public void setConsiderations(String considerations) {
        this.considerations = considerations;
    }
}
