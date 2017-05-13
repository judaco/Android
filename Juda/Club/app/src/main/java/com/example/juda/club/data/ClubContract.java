package com.example.juda.club.data;

import android.provider.BaseColumns;

/**
 * Created by Juda on 13/05/2017.
 */

public final class ClubContract {

    private ClubContract(){}

    public static final class ClubEntry implements BaseColumns {

        public final static String TABLE_NAME = "clubs";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_CLUB_NAME = "clubName";
        public final static String COLUMN_STADIUM_NAME = "stadiumName";
        public final static String COLUMN_STADIUM_CAPACITY = "stadiumCapacity";
        public final static String COLUMN_COUNTRY = "country";
        public final static String COLUMN_FOUNDATION_YEAR = "foundationYear";

    }
}
