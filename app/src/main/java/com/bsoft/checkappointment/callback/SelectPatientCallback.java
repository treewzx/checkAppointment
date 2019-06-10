package com.bsoft.checkappointment.callback;

import com.bsoft.checkappointment.model.PatientInfo;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/6/6.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
public interface SelectPatientCallback {
    public void onSelectReceiver(PatientInfo patientInfo);
}
