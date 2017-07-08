package com.example.juda.weathy.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.juda.weathy.DateConvert;

/**
 * Created by Juda on 07/07/2017.
 */

public class WeathyProvider extends ContentProvider {

    public static final int CODE_WEATHER = 100;
    public static final int CODE_WEATHER_AND_DATE = 101;

    private static final UriMatcher uriMatcher = buildUriMatcher();
    private WeathyDbHelper dbHelper;

    public static UriMatcher buildUriMatcher() {
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = WeathyContract.CONTENT_AUTHORITY;

        uriMatcher.addURI(authority, WeathyContract.PATH_WEATHER, CODE_WEATHER);
        uriMatcher.addURI(authority, WeathyContract.PATH_WEATHER + "/#", CODE_WEATHER_AND_DATE);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        dbHelper = new WeathyDbHelper(getContext());
        return true;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues [] values) {
        final SQLiteDatabase database = dbHelper.getWritableDatabase();

        switch (uriMatcher.match(uri)) {
            case CODE_WEATHER:
                database.beginTransaction();
                int rowsInserted = 0;
                try {
                    for (ContentValues contentValues : values) {
                        long weatherDate = contentValues.getAsLong(WeathyContract.WeathyEntry.COLUMN_DATE);
                        if (!DateConvert.isDateNormalized(weatherDate)) {
                            throw new IllegalArgumentException("Date must be normalized");
                        }
                        long _id = database.insert(WeathyContract.WeathyEntry.TABLE_NAME, null, contentValues);
                        if (_id != -1) {
                            rowsInserted++;
                        }
                    }
                    database.setTransactionSuccessful();
                } finally {
                    database.endTransaction();
                }
                if (rowsInserted > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }

                return rowsInserted;
            default:
                return super.bulkInsert(uri, values);
        }
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;

        switch (uriMatcher.match(uri)) {
            case CODE_WEATHER_AND_DATE: {
                String normalizedUtcDateStr = uri.getLastPathSegment();

                String[] selectionArguments = new String[]{normalizedUtcDateStr};

                cursor = dbHelper.getReadableDatabase().query(
                        WeathyContract.WeathyEntry.TABLE_NAME,
                        projection,
                        WeathyContract.WeathyEntry.COLUMN_DATE + " = ? ",
                        selectionArguments,
                        null, null, sortOrder);

                break;
            }
            case CODE_WEATHER: {
                cursor = dbHelper.getReadableDatabase().query(
                        WeathyContract.WeathyEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null, null, sortOrder);
                break;
            }
            default:
                throw new UnsupportedOperationException("unknown uri: " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
