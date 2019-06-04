package com.bsoft.common.utils;

import android.util.Log;

import com.bsoft.checkappointment.common.CancelAppointActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/5/30.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
public class DateUtil {

    //获取小时和分钟
    public static String getHM(String dateStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(simpleDateFormat.parse(dateStr));
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int min = calendar.get(Calendar.MINUTE);
            return ((hour < 10) ? ("0" + hour) : hour) + ":" + ((min < 10) ? ("0" + min) : min);
        } catch (ParseException e) {

        }
        return null;
    }

    //获取日期（*号）
    public static String getDay(String dateStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(simpleDateFormat.parse(dateStr));
            return String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        } catch (ParseException e) {

        }
        return null;
    }

    //获取日期（03-08）
    public static String getMD(String dateStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(simpleDateFormat.parse(dateStr));
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            return ((month + 1 < 10) ? ("0" + (month + 1)) : month + 1) + "-" + ((day < 10) ? ("0" + day) : day);
        } catch (ParseException e) {

        }
        return null;
    }

    //获取年月日（03-08）
    public static String getYMD(String dateStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(simpleDateFormat.parse(dateStr));
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            return new StringBuilder()
                    .append(year)
                    .append("-")
                    .append((month + 1 < 10) ? ("0" + (month + 1)) : month + 1)
                    .append("-")
                    .append((day < 10) ? ("0" + day) : day)
                    .toString();
        } catch (ParseException e) {

        }
        return null;
    }

    //获取年月日（03-08）
    public static String getYMDHM(String dateStr) {
        return new StringBuilder()
                .append(getYMD(dateStr))
                .append(" ")
                .append(getHM(dateStr))
                .toString();
    }

    public static long getTimeGap(String dateStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(simpleDateFormat.parse(dateStr));
            long endTime = calendar.getTimeInMillis();
            long beginTime = Calendar.getInstance().getTime().getTime();
            return (endTime - beginTime);
        } catch (ParseException e) {
            return 0;
        }

    }


}
