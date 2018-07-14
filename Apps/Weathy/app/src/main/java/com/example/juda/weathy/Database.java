package com.example.juda.weathy;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by Juda on 28/07/2017.
 */

public class Database extends SQLiteAssetHelper {

    private static final String DB_NAMES = "sqlitedata";
    private static final int DB_VERSION = 1;

    public Database(Context context) {
        super(context, DB_NAMES, null, DB_VERSION);
    }
}
