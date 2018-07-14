package com.example.juda.weathy;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Juda on 28/07/2017.
 */

public class DatabaseObject {

    private static Database database;
    private SQLiteDatabase sqLiteDatabase;

    public DatabaseObject(Context context) {
        database = new Database(context);
        this.database.getWritableDatabase();
        this.sqLiteDatabase = database.getReadableDatabase();
    }

    public SQLiteDatabase getDbConnection() {
        return this.sqLiteDatabase;
    }

    public void closeDbConnection(){
        if (this.sqLiteDatabase != null) {
            this.sqLiteDatabase.close();
        }
    }
}
