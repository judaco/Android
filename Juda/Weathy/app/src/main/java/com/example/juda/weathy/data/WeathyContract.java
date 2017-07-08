package com.example.juda.weathy.data;

import android.net.Uri;
import android.provider.BaseColumns;

import com.example.juda.weathy.DateConvert;

/**
 * Created by Juda on 02/07/2017.
 */

public class WeathyContract {

    public static final String CONTENT_AUTHORITY = "com.example.juda.weathy";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_WEATHER = "weather";

    public static final class WeathyEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_WEATHER).build();

        public final static String TABLE_NAME = "weathy";
        public final static String COLUMN_DATE = "date";
        public final static String COLUMN_WEATHY_ID = "weather_id";
        public final static String COLUMN_TEMP_MAX = "max";
        public final static String COLUMN_TEMP_MIN = "min";
        public final static String COLUMN_HUMIDITY = "humidity";
        public final static String COLUMN_WIND = "wind";
        public final static String COLUMN_PRESSURE = "pressure";
        public final static String COLUMN_DEGREES = "degrees";

        public static Uri weatherUriWithDateBuilder(long date) {
            return CONTENT_URI.buildUpon().appendPath(Long.toString(date)).build();
        }

        public static String getSqlSelectorForToday() {
            long normalizedUtcNow = DateConvert.normalizeDate(System.currentTimeMillis());
            return WeathyEntry.COLUMN_DATE + " >= " + normalizedUtcNow;
        }
    }
}
