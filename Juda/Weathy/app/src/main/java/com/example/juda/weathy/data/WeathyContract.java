package com.example.juda.weathy.data;

import android.provider.BaseColumns;

/**
 * Created by Juda on 02/07/2017.
 */

public class WeathyContract {

    public static final class WeathyEntry implements BaseColumns {

        public final static String TABLE_NAME = "weathy";
        public final static String COLUMN_DATE = "date";
        public final static String COLUMN_WEATHY_ID = "weather_id";
        public final static String COLUMN_TEMP_MAX = "max";
        public final static String COLUMN_TEMP_MIN = "min";
        public final static String COLUMN_HUMIDITY = "humidity";
        public final static String COLUMN_WIND = "wind";
        public final static String COLUMN_PRESSURE = "pressure";
        public final static String COLUMN_DEGREES = "degrees";
    }
}
