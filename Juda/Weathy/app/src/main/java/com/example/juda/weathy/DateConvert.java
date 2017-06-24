package com.example.juda.weathy;

import android.content.Context;
import android.text.format.DateUtils;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Created by Juda on 24/06/2017.
 */

public class DateConvert {

    public static final long SECOND_IN_MILLISEC = 1000;
    public static final long MINUTE_IN_MILLISEC = SECOND_IN_MILLISEC * 60;
    public static final long HOUR_IN_MILLISEC = MINUTE_IN_MILLISEC * 60;
    public static final long DAY_IN_MILLISEC = HOUR_IN_MILLISEC * 24;

    public static long getDayInMillis (long date) {
        TimeZone timeZone = TimeZone.getDefault();
        long gmtOffSetDate = timeZone.getOffset(date);
        return (date + gmtOffSetDate) / DAY_IN_MILLISEC;
    }

    public static long normalDate (long date) {
        long newValue = date / DAY_IN_MILLISEC * DAY_IN_MILLISEC;
        return newValue;
    }

    public static long getLocalDateFromUTC (long UTCDate) {
        TimeZone timeZone = TimeZone.getDefault();
        long gmtOffSetDate = timeZone.getOffset(UTCDate);
        return UTCDate - gmtOffSetDate;
    }

    public static long getUTCDateFromLocal (long localDate) {
        TimeZone timeZone = TimeZone.getDefault();
        long gmtOffSetDate = timeZone.getOffset(localDate);
        return gmtOffSetDate + localDate;
    }

    public static String dateString (Context context, long dateInMilliSec, boolean fullDate){
        long localDate = getLocalDateFromUTC(dateInMilliSec);
        long numDay = getDayInMillis(localDate);
        long currentNumDay = getDayInMillis(System.currentTimeMillis());

        if (numDay == currentNumDay || fullDate) {
            String dayName = getDayName(context, localDate);
            String readableDate = getReadableDate(context, localDate);
            if (numDay - currentNumDay < 2) {
                String locDayName = new SimpleDateFormat("EEEE").format(localDate);
                return readableDate.replace(locDayName, dayName);
            } else {
                return readableDate;
            }
        } else if (numDay < currentNumDay + 7) {
            return getDayName(context, localDate);
        } else {
            int flags = DateUtils.FORMAT_SHOW_DATE |
                    DateUtils.FORMAT_NO_YEAR |DateUtils.FORMAT_ABBREV_ALL |
                    DateUtils.FORMAT_SHOW_WEEKDAY;
            return DateUtils.formatDateTime(context, localDate, flags);
        }
    }

    private static String getReadableDate (Context context, long timeInMilliSec) {
        int flags = DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NO_YEAR | DateUtils.FORMAT_SHOW_WEEKDAY;
        return DateUtils.formatDateTime(context, timeInMilliSec, flags);
    }

    private static String getDayName (Context context, long dateInMilliSec) {
        long numDay = getDayInMillis(dateInMilliSec);
        long currentNumDay = getDayInMillis(System.currentTimeMillis());

        if (numDay == currentNumDay) {
            return context.getString(R.string.today);
        } else if (numDay == currentNumDay + 1) {
            return context.getString(R.string.tomorrow);
        } else {
            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
            return dayFormat.format(dateInMilliSec);
        }
    }
}
