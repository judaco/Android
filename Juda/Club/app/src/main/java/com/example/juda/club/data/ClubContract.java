package com.example.juda.club.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Juda on 13/05/2017.
 */

public final class ClubContract {

    public static final String CONTENT_AUTHORITY = "com.example.juda.club";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_CLUBS = "clubs";

    private ClubContract(){}

    public static final class ClubEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_CLUBS);

        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/"
                + CONTENT_AUTHORITY + "/" + PATH_CLUBS;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/"
                + CONTENT_AUTHORITY + "/" + PATH_CLUBS;

        public final static String TABLE_NAME = "clubs";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_CLUB_NAME = "clubName";
        public final static String COLUMN_STADIUM_NAME = "stadiumName";
        public final static String COLUMN_STADIUM_CAPACITY = "stadiumCapacity";
        public final static String COLUMN_COUNTRY = "country";
        public final static String COLUMN_FOUNDATION_YEAR = "foundationYear";

    }
}
