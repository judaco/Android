package com.example.juda.club.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.juda.club.data.ClubContract.ClubEntry;

import static com.example.juda.club.data.ClubContract.ClubEntry.TABLE_NAME;

/**
 * Created by Juda on 13/05/2017.
 */

public class ClubDBHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "clubs.db";

    public ClubDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_CLUBS_TABLE = "CREATE TABLE "
                + ClubEntry.TABLE_NAME + "("
                + ClubEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ClubEntry.COLUMN_CLUB_NAME + " TEXT, "
                + ClubEntry.COLUMN_STADIUM_NAME + " TEXT, "
                + ClubEntry.COLUMN_STADIUM_CAPACITY + " INTEGER, "
                + ClubEntry.COLUMN_COUNTRY + " TEXT, "
                + ClubEntry.COLUMN_FOUNDATION_YEAR + " INTEGER);";

        db.execSQL(SQL_CREATE_CLUBS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        //onCreate(db);
    }
}
