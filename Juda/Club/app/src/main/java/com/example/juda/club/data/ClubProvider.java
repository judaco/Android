package com.example.juda.club.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.example.juda.club.data.ClubContract.ClubEntry;

import static android.R.attr.cacheColorHint;
import static android.R.attr.fingerprintAuthDrawable;
import static android.R.attr.id;
import static android.R.attr.name;
import static android.R.attr.theme;

/**
 * Created by Juda on 20/05/2017.
 */

public class ClubProvider extends ContentProvider {

    private static final int CLUBS = 100;
    private static final int CLUB_ID = 101;
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    public static final String LOG_TAG = ClubProvider.class.getSimpleName();

    static {
        uriMatcher.addURI(ClubContract.CONTENT_AUTHORITY, ClubContract.PATH_CLUBS, CLUBS);
        uriMatcher.addURI(ClubContract.CONTENT_AUTHORITY, ClubContract.PATH_CLUBS + "/#", CLUB_ID);
    }

    private ClubDBHelper dbHelper;

    @Override
    public boolean onCreate() {
        dbHelper = new ClubDBHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        // Get readable database
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        // This cursor will hold the result of the query
        Cursor cursor;

        // Figure out if the URI matcher can match the URI to a specific code
        int match = uriMatcher.match(uri);
        switch (match) {
            case CLUBS:
                cursor = database.query(ClubEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case CLUB_ID:
                // For every "?" in the selection, we need to have an element in the selection
                // arguments that will fill in the "?". Since we have 1 question mark in the
                // selection, we have 1 String in the selection arguments' String array.
                selection = ClubEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                // This will perform a query on the pets table where the _id equals 3 to return a
                // Cursor containing that row of the table.
                cursor = database.query(ClubEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Can't query unknown Uri" + uri);
        }
        return cursor;
    }


    @Override
    public String getType(@NonNull Uri uri) {
        final int match = uriMatcher.match(uri);
        switch (match){
            case CLUBS:
                return ClubEntry.CONTENT_LIST_TYPE;
            case CLUB_ID:
                return ClubEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match" + match);
        }
    }


    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final int match = uriMatcher.match(uri);
        switch (match) {
            case CLUBS:
                return insertClub(uri, values);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    @Nullable
    private Uri insertClub(Uri uri, ContentValues values) {
        //if the club name key is present check that the name value is not null
        if (values.containsKey(ClubEntry.COLUMN_CLUB_NAME)) {
            // Check that the name is not null
            String name = values.getAsString(ClubEntry.COLUMN_CLUB_NAME);
            if (name == null) {
                throw new IllegalArgumentException("Please insert a club name");
            }
        }

        if (values.containsKey(ClubEntry.COLUMN_STADIUM_NAME)){
            String name = values.getAsString(ClubEntry.COLUMN_STADIUM_NAME);
            if (name == null){
                throw new IllegalArgumentException("Please insert a stadium name");
            }
        }

        if (values.containsKey(ClubEntry.COLUMN_STADIUM_CAPACITY)){
            Integer capacity = values.getAsInteger(ClubEntry.COLUMN_STADIUM_CAPACITY);
            if (capacity != null && capacity < 0){
                throw new IllegalArgumentException("Please insert a positive number");
            }
        }

        if (values.containsKey(ClubEntry.COLUMN_FOUNDATION_YEAR)){
            Integer year = values.getAsInteger(ClubEntry.COLUMN_FOUNDATION_YEAR);
            if (year < 1850 && year > 2017){
                throw new IllegalArgumentException("Please insert a number betwenn 1850 to 2017");
            }
        }

        // If there are no values to update, then don't try to update the database
        if (values.size() == 0) {
            return null;
        }

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        long id = database.insert(ClubContract.ClubEntry.TABLE_NAME, null, values);
        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }
        // Return the new URI with the ID (of the newly inserted row) appended at the end
        return ContentUris.withAppendedId(uri, id);
    }


    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        final int match = uriMatcher.match(uri);
        switch (match){
            case CLUBS:
                //delete all rows match both selection and selectionArgs
                return database.delete(ClubEntry.TABLE_NAME, selection, selectionArgs);
            case CLUB_ID:
                //delete a single row given by ID in the URI
                selection = ClubEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                return database.delete(ClubEntry.TABLE_NAME, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final int match = uriMatcher.match(uri);
        switch (match){
            case CLUBS:
                return updateClub(uri, values, selection, selectionArgs);
            case CLUB_ID:
                selection = ClubContract.ClubEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                return updateClub(uri, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update in not supported " + uri);
        }
    }
    private int updateClub (Uri uri, ContentValues values, String selection, String [] selectionArgs) {
        if (values.containsKey(ClubEntry.COLUMN_CLUB_NAME)) {
            // Check that the name is not null
            String name = values.getAsString(ClubEntry.COLUMN_CLUB_NAME);
            if (name == null) {
                throw new IllegalArgumentException("Please insert a club name");
            }
        }

        if (values.containsKey(ClubEntry.COLUMN_STADIUM_NAME)){
            String name = values.getAsString(ClubEntry.COLUMN_STADIUM_NAME);
            if (name == null){
                throw new IllegalArgumentException("Please insert a stadium name");
            }
        }

        if (values.containsKey(ClubEntry.COLUMN_STADIUM_CAPACITY)){
            Integer capacity = values.getAsInteger(ClubEntry.COLUMN_STADIUM_CAPACITY);
            if (capacity != null && capacity < 0){
                throw new IllegalArgumentException("Please insert a positive number");
            }
        }

        if (values.containsKey(ClubEntry.COLUMN_FOUNDATION_YEAR)){
            Integer year = values.getAsInteger(ClubEntry.COLUMN_FOUNDATION_YEAR);
            if (year < 1850 && year > 2017){
                throw new IllegalArgumentException("Please insert a number betwenn 1850 to 2017");
            }
        }
        if (values.size() == 0)
        return 0;

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        return database.update(ClubEntry.TABLE_NAME, values, selection, selectionArgs);
    }
}
