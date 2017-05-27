package com.example.juda.club;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.juda.club.data.ClubContract;
import com.example.juda.club.data.ClubContract.ClubEntry;
import com.example.juda.club.data.ClubDBHelper;
import com.example.juda.club.data.ClubProvider;

public class ClubActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    //private ClubDBHelper dbHelper;

    private static final int CLUB_LOADER = 0;

    ClubCursorAdapter clubCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClubActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        ListView listView = (ListView)findViewById(R.id.list);

        View emptyView = findViewById(R.id.empty_view);
        listView.setEmptyView(emptyView);

        clubCursorAdapter = new ClubCursorAdapter(this, null, 0);
        listView.setAdapter(clubCursorAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ClubActivity.this, EditorActivity.class);

                Uri currentUri = ContentUris.withAppendedId(ClubEntry.CONTENT_URI, id);

                intent.setData(currentUri);

                startActivity(intent);
            }
        });

        //remove the loader
        getLoaderManager().initLoader(CLUB_LOADER, null, this);

        //dbHelper = new ClubDBHelper(this);
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        displayDatabaseInfo();
//    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the pets database.
     */
    //private void displayDatabaseInfo() {
        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        //ClubDBHelper dbHelper = new ClubDBHelper(this);

        // Create and/or open a database to read from it
        //SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Perform this raw SQL query "SELECT * FROM pets"
        // to get a Cursor that contains all rows from the pets table.
        //Cursor cursor = db.rawQuery("SELECT * FROM " + ClubContract.ClubEntry.TABLE_NAME, null);

//        String [] projection = {
//                ClubEntry._ID, ClubEntry.COLUMN_CLUB_NAME, ClubEntry.COLUMN_STADIUM_NAME,
//                ClubEntry.COLUMN_STADIUM_CAPACITY, ClubEntry.COLUMN_COUNTRY, ClubEntry.COLUMN_FOUNDATION_YEAR
//        };
//
//        Cursor cursor = getContentResolver().query(ClubEntry.CONTENT_URI, projection, null, null, null, null);

//        ListView listView = (ListView)findViewById(R.id.list);
//
//        ClubCursorAdapter adapter = new ClubCursorAdapter(this, cursor, 0);
//
//        listView.setAdapter(adapter);
//
//        View emptyView = findViewById(R.id.empty_view);
//        listView.setEmptyView(emptyView);

//        Cursor cursor = db.query(ClubEntry.TABLE_NAME,
//                projection, null, null, null, null, null, null);

//        try {
//            // Display the number of rows in the Cursor (which reflects the number of rows in the
//            // pets table in the database).
////            TextView displayView = (TextView) findViewById(R.id.txtview_club);
////            displayView.setText("Number of rows in clubs database table: " + cursor.getCount());
//
//
//            // In the while loop below, iterate through the rows of the cursor and display
//            // the information from each column in this order.
//            displayView.setText("The Clubs table " + cursor.getCount() + " clubs\n\n");
//            displayView.append(ClubEntry._ID + " - "
//                + ClubEntry.COLUMN_CLUB_NAME + " - "
//                + ClubEntry.COLUMN_STADIUM_NAME + " - "
//                + ClubEntry.COLUMN_STADIUM_CAPACITY + " - "
//                + ClubEntry.COLUMN_COUNTRY + " - "
//                + ClubEntry.COLUMN_FOUNDATION_YEAR + "\n"
//            );
//
//            // Figure out the index of each column
//            int idColumnIndex = cursor.getColumnIndex(ClubEntry._ID);
//            int clubNameColumnIndex = cursor.getColumnIndex(ClubEntry.COLUMN_CLUB_NAME);
//            int stadiumNameColumnIndex = cursor.getColumnIndex(ClubEntry.COLUMN_STADIUM_NAME);
//            int stadiumCapacityColumnIndex = cursor.getColumnIndex(ClubEntry.COLUMN_STADIUM_CAPACITY);
//            int countryColumnIndex = cursor.getColumnIndex(ClubEntry.COLUMN_COUNTRY);
//            int foundationColumnIndex = cursor.getColumnIndex(ClubEntry.COLUMN_FOUNDATION_YEAR);
//
//            // Iterate through all the returned rows in the cursor
//            while (cursor.moveToNext()){
//                // Use that index to extract the String or Int value of the word
//                // at the current row the cursor is on.
//                int currentID = cursor.getInt(idColumnIndex);
//                String currentClubName = cursor.getString(clubNameColumnIndex);
//                String currentStadiumName = cursor.getString(stadiumNameColumnIndex);
//                int currentStadiumCapacity = cursor.getInt(stadiumCapacityColumnIndex);
//                String currentCountry = cursor.getString(countryColumnIndex);
//                int currentFoundationYear = cursor.getInt(foundationColumnIndex);
//
//                // Display the values from each column of the current row in the cursor in the TextView
//                displayView.append(("\n" + currentID + " - "
//                + currentClubName + " - "
//                + currentStadiumName + " - "
//                + currentStadiumCapacity + " - "
//                + currentCountry + " - "
//                + currentFoundationYear));
        //    }
     //   } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
           // cursor.close();
       // }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_club, menu);
        return true;
    }

    private void insertClub(){
        // Gets the data repository in write mode
        //SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();

        values.put(ClubEntry.COLUMN_CLUB_NAME, "Manchester");
        values.put(ClubEntry.COLUMN_STADIUM_NAME, "Etihad");
        values.put(ClubEntry.COLUMN_STADIUM_CAPACITY, 65000);
        values.put(ClubEntry.COLUMN_COUNTRY, "England");
        values.put(ClubEntry.COLUMN_FOUNDATION_YEAR, 1866);

        Uri newUri = getContentResolver().insert(ClubEntry.CONTENT_URI, values);

        // Insert the new row, returning the primary key value of the new row
        //long newRowId = db.insert(ClubEntry.TABLE_NAME, null, values);

        //Log.v("ClubActivity", "New row id " + newRowId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                // Do nothing for now
                insertClub();
                //displayDatabaseInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String [] projection = {
                ClubEntry._ID,
                ClubEntry.COLUMN_CLUB_NAME,
                ClubEntry.COLUMN_STADIUM_NAME,
                ClubEntry.COLUMN_STADIUM_CAPACITY,
                ClubEntry.COLUMN_COUNTRY,
                ClubEntry.COLUMN_FOUNDATION_YEAR
        };
        return new CursorLoader(this,
                ClubEntry.CONTENT_URI,
                projection,
                null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        clubCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        clubCursorAdapter.swapCursor(null);
    }
}
