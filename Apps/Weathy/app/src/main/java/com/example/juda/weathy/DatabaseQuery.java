package com.example.juda.weathy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.juda.weathy.DatabaseLocationObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juda on 28/07/2017.
 */

public class DatabaseQuery extends DatabaseObject{

    private final String TABLE_NAME = "data";
    private final String KEY_NAME = "_id";

    public DatabaseQuery(Context context) {
        super(context);
    }

    public List<DatabaseLocationObject> getStoredDataLocations(){
        List<DatabaseLocationObject> allLocations = new ArrayList<DatabaseLocationObject>();
        String query = "Select * from data";
        Cursor cursor = this.getDbConnection().rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                System.out.println("number " + id);
                String storedData = cursor.getString(cursor.getColumnIndexOrThrow("content"));
                System.out.println("number " + storedData);
                allLocations.add(new DatabaseLocationObject(id, storedData));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return allLocations;
    }

    public int countStoredLocations(){
        int total = 0;
        String q = "Select * from data";
        Cursor cursor = this.getDbConnection().rawQuery(q, null);
        if (cursor.moveToFirst()) {
            total = cursor.getCount();
        }
        cursor.close();
        return total;
    }

    public void addNewLocation(String cityCountry) {
        ContentValues values = new ContentValues();
        values.put("content", cityCountry);;
        getDbConnection().insert(TABLE_NAME, null, values);
    }

    public boolean deleteLocation(int locationId) {
        return getDbConnection().delete(TABLE_NAME, KEY_NAME + "=" + locationId, null) > 0;
    }

    public void deleteAllData() {
        getDbConnection().execSQL("delete from " + TABLE_NAME);
    }
}
