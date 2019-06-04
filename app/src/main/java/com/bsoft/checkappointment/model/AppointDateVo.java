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
public class AppointDateVo implements Parcelable {


    /**
     * appointmentDate : 2019-05-31 00:00:00
     * dayOfTheWeek : 五
     */

    private String appointmentDate;
    private String dayOfTheWeek;
    private boolean isSelected;

    public AppointDateVo() {

    }

    protected AppointDateVo(Parcel in) {
        appointmentDate = in.readString();
        dayOfTheWeek = in.readString();
        isSelected = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(appointmentDate);
        dest.writeString(dayOfTheWeek);
        dest.writeByte((byte) (isSelected ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppointDateVo> CREATOR = new Creator<AppointDateVo>() {
        @Override
        public AppointDateVo createFromParcel(Parcel in) {
            return new AppointDateVo(in);
        }

        @Override
        public AppointDateVo[] newArray(int size) {
            return new AppointDateVo[size];
        }
    };

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getDayOfTheWeek() {
        return dayOfTheWeek;
    }

    public void setDayOfTheWeek(String dayOfTheWeek) {
        this.dayOfTheWeek = dayOfTheWeek;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


}
