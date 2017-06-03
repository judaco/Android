package com.example.juda.club;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.juda.club.data.ClubContract.ClubEntry;

import com.example.juda.club.data.ClubContract;
import com.example.juda.club.data.ClubDBHelper;

import java.nio.channels.ClosedByInterruptException;

/**
     * Allows user to create a new pet or edit an existing one.
     */
    public class EditorActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int EXISTING_CLUB_LOADER = 0;

    private boolean clubHasChanged = false;

    private View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            clubHasChanged = true;
            return false;
        }
    };

    private Uri currentClubUri;

        private EditText clubName;
        private EditText stadiumName;
        private EditText stadiumCapacity;
        private EditText foundationYear;
        private Spinner country;

        private String mCountry;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_editor);

            Intent intent = getIntent();
            currentClubUri = intent.getData();

            if (currentClubUri == null){
                setTitle(getString(R.string.editor_activity_title_new_club));
                invalidateOptionsMenu();
            }else {
                setTitle(getString(R.string.editor_activity_title_edit_club));
            }

            // Find all relevant views that we will need to read user input from
            clubName = (EditText) findViewById(R.id.edit_club_name);
            stadiumName = (EditText) findViewById(R.id.edit_stadium_name);
            stadiumCapacity = (EditText) findViewById(R.id.edit_stadium_capacity);
            country = (Spinner) findViewById(R.id.spinner_country);
            foundationYear = (EditText) findViewById(R.id.edit_foundation_year);

            clubName.setOnTouchListener(touchListener);
            stadiumName.setOnTouchListener(touchListener);
            stadiumCapacity.setOnTouchListener(touchListener);
            country.setOnTouchListener(touchListener);
            foundationYear.setOnTouchListener(touchListener);

            setupSpinner();

            getLoaderManager().initLoader(EXISTING_CLUB_LOADER, null, this);
        }

        /**
         * Setup the dropdown spinner that allows the user to select the gender of the pet.
         */
        private void setupSpinner() {
            // Create adapter for spinner. The list options are from the String array it will use
            // the spinner will use the default layout
            ArrayAdapter spinnerAdapter = ArrayAdapter.createFromResource(this,
                    R.array.array_country_options, android.R.layout.simple_spinner_item);

            // Specify dropdown layout style - simple list view with 1 item per line
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

            // Apply the adapter to the spinner
            country.setAdapter(spinnerAdapter);

            // Set the integer mSelected to the constant values
            country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String selection = (String) parent.getItemAtPosition(position);
                }

                // Because AdapterView is an abstract class, onNothingSelected must be defined
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    mCountry = "Unknown"; // Unknown
                }
            });
        }

        private void saveClub() {
            String nameString = clubName.getText().toString().trim();
            String stadiumNameString = stadiumName.getText().toString().trim();
            int capacity = Integer.parseInt(stadiumCapacity.getText().toString().trim());
            int foundation = Integer.parseInt(foundationYear.getText().toString().trim());

            if (currentClubUri == null &&
                TextUtils.isEmpty(nameString) && TextUtils.isEmpty(stadiumNameString))
            {
                return;
            }

            //ClubDBHelper dbHelper = new ClubDBHelper(this);

            // Gets the data repository in write mode
            //SQLiteDatabase db = dbHelper.getWritableDatabase();
            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();

            values.put(ClubEntry.COLUMN_CLUB_NAME, nameString);
            values.put(ClubEntry.COLUMN_STADIUM_NAME, stadiumNameString);
            values.put(ClubEntry.COLUMN_STADIUM_CAPACITY, capacity);
            values.put(ClubEntry.COLUMN_COUNTRY, mCountry);
            values.put(ClubEntry.COLUMN_FOUNDATION_YEAR, foundation);

            // Determine if this is a new or existing pet by checking if mCurrentPetUri is null or not
            if (currentClubUri == null) {

                // This is a NEW pet, so insert a new pet into the provider,
                // returning the content URI for the new club.
                Uri newUri = getContentResolver().insert(ClubEntry.CONTENT_URI, values);

                if (newUri == null){
                    Toast.makeText(this, "Error with saving new club", Toast.LENGTH_SHORT).show();
                }else{
                    int rowsAffected = getContentResolver().update(ClubEntry.CONTENT_URI, values, null, null);

                    if (rowsAffected == 0){
                        Toast.makeText(this, "Error with updating a club", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(this, "The club updated", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu options from the res/menu/menu_editor.xml file.
            // This adds menu items to the app bar.
            getMenuInflater().inflate(R.menu.menu_editor, menu);
            return true;
        }

         @Override
         public boolean onPrepareOptionsMenu(Menu menu) {
            super.onPrepareOptionsMenu(menu);

             if (currentClubUri == null){
                 MenuItem menuItem = menu.findItem(R.id.action_delete);
                 menuItem.setVisible(false);
             }

             return true;
    }

    @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // User clicked on a menu option in the app bar overflow menu
            switch (item.getItemId()) {
                // Respond to a click on the "Save" menu option
                case R.id.action_save:
                    //Save a clubs to database
                    saveClub();
                    //Exit Activity
                    finish();
                    return true;
                // Respond to a click on the "Delete" menu option
                case R.id.action_delete:
                    // Do nothing for now
                    return true;
                // Respond to a click on the "Up" arrow button in the app bar
                case android.R.id.home:
                    // Navigate back to parent activity (CatalogActivity)
                    if (!clubHasChanged) {
                        NavUtils.navigateUpFromSameTask(EditorActivity.this);
                        return true;
                    }
                    DialogInterface.OnClickListener discardButtonClickListener =
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // User clicked "Discard" button, navigate to parent activity.
                                    NavUtils.navigateUpFromSameTask(EditorActivity.this);
                                }
                            };
                    showUnsavedChangesDialog(discardButtonClickListener);
                    return true;
            }
            return super.onOptionsItemSelected(item);
        }

    @Override
    public void onBackPressed() {
        if (!clubHasChanged) {
            super.onBackPressed();
            return;
        }

        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                };
                showUnsavedChangesDialog(discardButtonClickListener);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        if (currentClubUri == null){
            return null;
        }

        String projection [] = {
                ClubEntry._ID,
                ClubEntry.COLUMN_CLUB_NAME,
                ClubEntry.COLUMN_STADIUM_NAME,
                ClubEntry.COLUMN_STADIUM_CAPACITY,
                ClubEntry.COLUMN_COUNTRY,
                ClubEntry.COLUMN_FOUNDATION_YEAR
        };
        return new CursorLoader(this,
                currentClubUri,
                projection,
                null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        if (data == null || data.getCount() < 1){
            return;
        }

        if (data.moveToFirst()) {
            // Find the columns of pet attributes that we're interested in
            int clubNameColumnIndex = data.getColumnIndex(ClubEntry.COLUMN_CLUB_NAME);
            int stadiumNameColumnIndex = data.getColumnIndex(ClubEntry.COLUMN_STADIUM_NAME);
            int stadiumCapacityColumnIndex = data.getColumnIndex(ClubEntry.COLUMN_STADIUM_CAPACITY);
            //int countryColumnIndex = data.getColumnIndex(ClubEntry.COLUMN_COUNTRY);
            int foundationColumnIndex = data.getColumnIndex(ClubEntry.COLUMN_FOUNDATION_YEAR);

            // Extract out the value from the Cursor for the given column index
            String currentClubName = data.getString(clubNameColumnIndex);
            String currentStadiumName = data.getString(stadiumNameColumnIndex);
            int currentStadiumCapacity = data.getInt(stadiumCapacityColumnIndex);
            //String currentCountry = data.getString(countryColumnIndex);
            int currentFoundationYear = data.getInt(foundationColumnIndex);

            // Update the views on the screen with the values from the database
            clubName.setText(currentClubName);
            stadiumName.setText(currentStadiumName);
            stadiumCapacity.setText(String.valueOf(currentStadiumCapacity));
            foundationYear.setText(String.valueOf(currentFoundationYear));
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // If the loader is invalidated, clear out all the data from the input fields.
        clubName.setText("");
        stadiumName.setText("");
        stadiumCapacity.setText("");
        foundationYear.setText("");
    }

    private void showUnsavedChangesDialog(
        DialogInterface.OnClickListener discardButtonClickListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unsaved_changes_dialog_msg);
        builder.setPositiveButton(R.string.discard, discardButtonClickListener);
        builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialog != null){
                    dialog.dismiss();
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
