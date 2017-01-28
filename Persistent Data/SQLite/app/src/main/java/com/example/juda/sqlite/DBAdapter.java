package com.example.juda.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Juda on 28/01/2017.
 */

public class DBAdapter {
    static final String KEY_PERSON_ID = "PersonID";
    static final String KEY_FIRST_NAME = "FirstName";
    static final String KEY_LAST_NAME = "LastName";
    static final String KEY_BIRTH_YEAR = "BirthYear";

    static final String DATABASE_NAME = "MyDB";
    static final String TABLE_NAME = "Persons";
    static final int DATABASE_VERSION = 1;

    static final String TABLE_PERSONS_CREATE = "CREATE TABLE " + TABLE_NAME + "(" + KEY_PERSON_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_FIRST_NAME + " TEXT NOT NULL, " + KEY_LAST_NAME +
            " TEXT, " + KEY_BIRTH_YEAR + " INTEGER)";

    private Context context;
    private SQLiteDatabase db;
    private DatabaseHelper helper;

    public DBAdapter(Context context) {//constructor of our Adapter
        this.context = context;
        helper = new DatabaseHelper(context);
    }

    public void open() {
        if (db == null)
            db = helper.getWritableDatabase();
    }

    public void close() {
        if (db != null) {
            db.close();
            db = null;
            helper.close();//?
        }
    }

    public long insertPerson(String firstName, String lastName, int birthYear) {
        if (db == null)
            return -1L;
        ContentValues values = new ContentValues();//like Bundle - insert pairs of key and value
        values.put(KEY_FIRST_NAME, firstName);
        values.put(KEY_LAST_NAME, lastName);
        values.put(KEY_BIRTH_YEAR, birthYear);
        return db.insert(TABLE_NAME, null, values);
    }

    public boolean deletePerson(long personId) {
        if (db == null)
            return false;
        // db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + KEY_PERSON_ID + "=" + personId);//SQL lang as we have in the method delete (same as insert method)
        return db.delete(TABLE_NAME, KEY_PERSON_ID + "=?", new String[]{String.valueOf(personId)}) > 0;
        //by the number of the "?", the array knows how much values of PersonId to search.
        // > 0 = means more than 1 row deleted, cause the personId is Unique, so for sure it' has been deleted, but I will tell you how much rows I deleted
    }

    public Cursor getPersons() {
        if (db == null)
            return null;
        return db.query(TABLE_NAME, new String[]{KEY_PERSON_ID, KEY_FIRST_NAME, KEY_LAST_NAME, KEY_BIRTH_YEAR},
                null, null, null, null, null);
    }

    public boolean updatePerson(int personId, String firstName, String lastName, int birthYear) {
        if (db == null)
            return false;
        ContentValues values = new ContentValues();
        values.put(KEY_FIRST_NAME, firstName);
        values.put(KEY_LAST_NAME, lastName);
        values.put(KEY_BIRTH_YEAR, birthYear);
        return db.update(TABLE_NAME, values, KEY_PERSON_ID + "=?", new String[]{String.valueOf(personId)}) > 0;
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {//nested class

        public DatabaseHelper(Context context) {//constructor of nested class
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {//set up automatic by software (maarechet hafaala)
            onUpgrade(db, 0, DATABASE_VERSION);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {//if the version is old on the DATABASE_VERSION, then this method is setting up to upgrade the version
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);//destroy the table itself, not only the database
            db.execSQL(TABLE_PERSONS_CREATE);
        }
    }
}
