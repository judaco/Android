package com.example.juda.weathy;

import android.content.Context;
import android.text.format.DateUtils;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import static android.text.format.DateUtils.DAY_IN_MILLIS;

/**
 * Created by Juda on 24/06/2017.
 */

public class DateConvert {

    //public static final long SECOND_IN_MILLISEC = 1000;
    //public static final long MINUTE_IN_MILLISEC = SECOND_IN_MILLISEC * 60;
    //public static final long HOUR_IN_MILLISEC = MINUTE_IN_MILLISEC * 60;
    //public static final long DAY_IN_MILLISEC = HOUR_IN_MILLISEC * 24;

    /**This method returns the number of milliseconds (UTC time) for today's date at midnight in the local time zone*/
    public static long getNormalUTCDateForToday() {
        //Time in milliseconds elapsed since Epoch (January 1, 1970, 00:00, GMT)
        long utcNowInMillis = System.currentTimeMillis();
        //The current time zone of the device
        TimeZone currentTZ = TimeZone.getDefault();
        //The number in milliseconds adding to UTC time
        long gmtOffsetInMillis = currentTZ.getOffset(utcNowInMillis);
        //The number in milliseconds since Epoch
        long timeSinceEpochLocalTimeInMillis = utcNowInMillis + gmtOffsetInMillis;
        //Conversion of milliseconds to days (disregarding any fractional days)
        long millisToDays = TimeUnit.MILLISECONDS.toDays(timeSinceEpochLocalTimeInMillis);
        //Today's date in GMT at 00:00 in milliseconds
        long normalUTCMidnightInMillis = TimeUnit.DAYS.toMillis(timeSinceEpochLocalTimeInMillis);

        return normalUTCMidnightInMillis;
    }
    /**The number of days since the Epoch in UTC from the current date*/
    private static long elapsedDaysSinceEpoch(long utcDate) {
        return TimeUnit.MILLISECONDS.toDays(utcDate);
    }

    /**Conversion of the given date in milliseconds to date in UTC at 00:00*/
    public static long normalizeDate (long date) {
        long daysSinceEpoch = elapsedDaysSinceEpoch(date);
        long millisSinceEpochTillTodayMidnightUtc = daysSinceEpoch * DAY_IN_MILLIS;
        return millisSinceEpochTillTodayMidnightUtc;
    }

    /**Ensuring the dates are normalized before inserting them into Provider*/
    public static boolean isDateNormalized(long millisSinceEpoch) {
        boolean isDateNormalized = false;
        if (millisSinceEpoch % DAY_IN_MILLIS == 0) {
            isDateNormalized = true;
        }
        return isDateNormalized;
    }
    /**The local time 00:00, for the provided normalized UTC date*/
    private static long getLocalMidnightFromNormalizedUtcDate(long normUtcdate) {
        TimeZone tz = TimeZone.getDefault();
        long gmtOffset = tz.getOffset(normUtcdate);
        long localMidnightinMillis = normUtcdate - gmtOffset;
        return localMidnightinMillis;
    }

    /**A method who converts the db date into a display*/
    public static String dateConvertToString(Context context, long utcMidnight, boolean fullDate) {
        //localDate = local date at 00:00 in milliseconds
        long localDate = getLocalMidnightFromNormalizedUtcDate(utcMidnight);
        //The number of days elapsed since the Epoch
        long daysSinceEpochForDateProvided = elapsedDaysSinceEpoch(localDate);
        //The number of days since the Epoch until today
        long daysSinceEpochTillToday = elapsedDaysSinceEpoch(System.currentTimeMillis());

        if (daysSinceEpochForDateProvided == daysSinceEpochTillToday || fullDate) {
            String dayName = getDayName(context, localDate);
            String readableDate = getReadableDate(context, localDate);
            if (daysSinceEpochForDateProvided - daysSinceEpochTillToday < 2) {
                String locDayName = new SimpleDateFormat("EEEE").format(localDate);
                return readableDate.replace(locDayName, dayName);
            } else {
                return readableDate;
            }
        } else if (daysSinceEpochForDateProvided < daysSinceEpochTillToday + 7) {
            //if the input is less than a week in the future, return dayName
            return getDayName(context, localDate);
        } else {
            int flags = DateUtils.FORMAT_SHOW_DATE
                    | DateUtils.FORMAT_NO_YEAR
                    | DateUtils.FORMAT_ABBREV_ALL
                    | DateUtils.FORMAT_SHOW_WEEKDAY;

            return DateUtils.formatDateTime(context, localDate, flags);
        }
    }

    /**Date in the format, which shows an abbr. date, without a year*/
    private static String getReadableDate (Context context, long timeInMilliSec) {
        int flags = DateUtils.FORMAT_SHOW_DATE
                | DateUtils.FORMAT_NO_YEAR
                | DateUtils.FORMAT_SHOW_WEEKDAY;

        return DateUtils.formatDateTime(context, timeInMilliSec, flags);
    }

    private static String getDayName(Context context, long dateInMillis) {
        long daysSinceEpochForDateProvided = elapsedDaysSinceEpoch(dateInMillis);
        long daysSinceEpochTillToday = elapsedDaysSinceEpoch(System.currentTimeMillis());

        int daysAfterToday = (int) (daysSinceEpochForDateProvided - daysSinceEpochTillToday);

        switch (daysAfterToday) {
            case 0:
                return context.getString(R.string.today);
            case 1:
                return context.getString(R.string.tomorrow);
            default:
                SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
                return dayFormat.format(dateInMillis);
        }
    }
}

/*
 if (daysSinceEpochForDateProvided == daysSinceEpochTillToday) {
            return context.getString(R.string.today);
        } else if (daysSinceEpochForDateProvided == daysSinceEpochTillToday + 1) {
            return context.getString(R.string.tomorrow);
        } else {
            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
            return dayFormat.format(dateInMillis);
        }
    }
}


    public static long getDayInMillis (long date) {
        TimeZone timeZone = TimeZone.getDefault();
        long gmtOffSetDate = timeZone.getOffset(date);
        return (date + gmtOffSetDate) / DAY_IN_MILLIS;
    }

    public static long normalDate (long date) {
        long newValue = date / DAY_IN_MILLIS * DAY_IN_MILLIS;
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

*/