package com.jingoteam.ngocphong.miniproject2_v1;

import org.joda.time.DateTime;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by NgocPhong on 31/05/2016.
 */
public class DateManager {
    public static String convertDateToString(Date d) {
        DateFormat currentDateTimeString = DateFormat.getDateTimeInstance();
        currentDateTimeString.setTimeZone(TimeZone.getTimeZone("GMT+7"));
        String gmtTime = currentDateTimeString.format(d);

        return gmtTime;
    }

    public static Date addDate(Date d, int number){
        String date1 = convertDateToString(d);

        Date date2 = new DateTime(d).minusDays(1).toDate();
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(d);
//        calendar.add(Calendar.DAY_OF_YEAR, number);

        String aa = convertDateToString(date2);
        return date2;
    }
}
