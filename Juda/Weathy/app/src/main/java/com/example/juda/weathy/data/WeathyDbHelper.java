package com.example.juda.weathy.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.juda.weathy.data.WeathyContract.WeathyEntry;


/**
 * Created by Juda on 02/07/2017.
 */

public class WeathyDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "weathy.db";

    private static final int DATABASE_VER = 1;

    public WeathyDbHelper(Context context) {
        super(context, DATABASE_NAME, null , DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String CREATE_WEATHY_TABLE = "CREATE TABLE " + WeathyEntry.TABLE_NAME + " (" +
                WeathyEntry._ID + "INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WeathyEntry.COLUMN_DATE + " INTEGER, " +
                WeathyEntry.COLUMN_WEATHY_ID + " INTEGER, " +
                WeathyEntry.COLUMN_TEMP_MAX + " REAL, " +
                WeathyEntry.COLUMN_TEMP_MIN + " REAL, " +
                WeathyEntry.COLUMN_HUMIDITY + " REAL, " +
                WeathyEntry.COLUMN_WIND + " REAL, " +
                WeathyEntry.COLUMN_PRESSURE + " REAL, " +
                WeathyEntry.COLUMN_DEGREES + " REAL " + ");";

        db.execSQL(CREATE_WEATHY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
