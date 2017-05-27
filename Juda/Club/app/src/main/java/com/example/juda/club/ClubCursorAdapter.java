package com.example.juda.club;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.juda.club.data.ClubContract;
import com.example.juda.club.data.ClubContract.ClubEntry;

import static android.R.attr.name;
import static android.R.attr.priority;

/**
 * Created by Juda on 27/05/2017.
 */

public class ClubCursorAdapter extends CursorAdapter {


    public ClubCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        //The newView method is used to inflate a new view and return it
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        // Find fields to populate in inflated template
        TextView nameTV = (TextView) view.findViewById(R.id.name);
        TextView summaryTV = (TextView) view.findViewById(R.id.summary);

        int clubNameColumnIndex = cursor.getColumnIndex(ClubEntry.COLUMN_CLUB_NAME);
        int stadiumNameColumnIndex = cursor.getColumnIndex(ClubEntry.COLUMN_STADIUM_NAME);

        // Extract properties from cursor
        String clubName = cursor.getString(clubNameColumnIndex);
        String stadiumName = cursor.getString(stadiumNameColumnIndex);
        // Populate fields with extracted properties
        nameTV.setText(clubName);
        summaryTV.setText(stadiumName);

//        // The bindView method is used to bind all data to a given view
//        EditText clubName = (EditText) view.findViewById(R.id.edit_club_name);
//        EditText stadiumName = (EditText) view.findViewById(R.id.edit_stadium_name);
//        EditText stadiumCapacity = (EditText) view.findViewById(R.id.edit_stadium_capacity);
//        EditText foundationYear = (EditText) view.findViewById(R.id.edit_foundation_year);
//
//        int clubNameColumnIndex = cursor.getColumnIndex(ClubEntry.COLUMN_CLUB_NAME);
//        int stadiumNameColumnIndex = cursor.getColumnIndex(ClubEntry.COLUMN_STADIUM_NAME);
//        int stadiumCapacityColumnIndex = cursor.getColumnIndex(ClubEntry.COLUMN_STADIUM_CAPACITY);
//        int foundationColumnIndex = cursor.getColumnIndex(ClubEntry.COLUMN_FOUNDATION_YEAR);
//
//        // Extract properties from cursor
//        String currentClubName = cursor.getString(clubNameColumnIndex);
//        String currentStadiumName = cursor.getString(stadiumNameColumnIndex);
//        int currentStadiumCapacity = cursor.getInt(stadiumCapacityColumnIndex);
//        int currentFoundationYear = cursor.getInt(foundationColumnIndex);
//
//        // Populate fields with extracted properties
//        clubName.setText(currentClubName);
//        stadiumName.setText(currentStadiumName);
//        stadiumCapacity.setText(String.valueOf(currentStadiumCapacity));
//        foundationYear.setText(String.valueOf(currentFoundationYear));

    }
}
