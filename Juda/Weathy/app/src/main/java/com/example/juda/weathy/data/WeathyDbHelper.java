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

    private static final int DATABASE_VER = 3;

    public WeathyDbHelper(Context context) {
        super(context, DATABASE_NAME, null , DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String CREATE_WEATHY_TABLE = "CREATE TABLE " + WeathyEntry.TABLE_NAME + " (" +
                WeathyEntry._ID + "INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WeathyEntry.COLUMN_DATE + " INTEGER NOT NULL, " +
                WeathyEntry.COLUMN_WEATHY_ID + " INTEGER NOT NULL," +
                WeathyEntry.COLUMN_TEMP_MAX + " REAL NOT NULL, " +
                WeathyEntry.COLUMN_TEMP_MIN + " REAL NOT NULL, " +
                WeathyEntry.COLUMN_HUMIDITY + " REAL NOT NULL, " +
                WeathyEntry.COLUMN_WIND + " REAL NOT NULL, " +
                WeathyEntry.COLUMN_PRESSURE + " REAL NOT NULL, " +
                WeathyEntry.COLUMN_DEGREES + " REAL NOT NULL, " +

        " UNIQUE (" + WeathyEntry.COLUMN_DATE + ") ON CONLICT REPLACE);";

        db.execSQL(CREATE_WEATHY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + WeathyEntry.TABLE_NAME);
        onCreate(db);
    }
}
