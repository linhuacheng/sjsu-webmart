package com.sjsu.webmart.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created with IntelliJ IDEA.
 * User: ckempaiah
 * Date: 8/8/12
 * Time: 12:35 AM
 * To change this template use File | Settings | File Templates.
 */
public class FilterUtil {

    public static Date getMidnight(Date time) {
        Calendar date = new GregorianCalendar();
        date.setTime(time);
        // reset hour, minutes, seconds and millis
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);

        return date.getTime();
    }

    public static Date  getNextDay(Date time) {
        Calendar date = new GregorianCalendar();
        date.setTime(time);
        // reset hour, minutes, seconds and millis
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);

        // next day
        date.add(Calendar.DAY_OF_MONTH, 1);
        return date.getTime();
    }
}
