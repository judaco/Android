package com.example.juda.club;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.juda.club.data.ClubContract.ClubEntry;

import com.example.juda.club.data.ClubContract;
import com.example.juda.club.data.ClubDBHelper;

/**
     * Allows user to create a new pet or edit an existing one.
     */
    public class EditorActivity extends AppCompatActivity {

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
            Uri currentUri = intent.getData();

            if (currentUri == null){
                setTitle(getString(R.string.editor_activity_title_new_club));
            }else {
                setTitle(getString(R.string.editor_activity_title_edit_club));
            }

            // Find all relevant views that we will need to read user input from
            clubName = (EditText) findViewById(R.id.edit_club_name);
            stadiumName = (EditText) findViewById(R.id.edit_stadium_name);
            stadiumCapacity = (EditText) findViewById(R.id.edit_stadium_capacity);
            country = (Spinner) findViewById(R.id.spinner_country);
            foundationYear = (EditText) findViewById(R.id.edit_foundation_year);

            setupSpinner();
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

        private void insertClub(){
            String nameString = clubName.getText().toString().trim();
            String stadiumNameString = stadiumName.getText().toString().trim();
            int capacity = Integer.parseInt(stadiumCapacity.getText().toString().trim());
            int foundation = Integer.parseInt(foundationYear.getText().toString().trim());

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

            // Insert a new club into the provider, returning the content URI for the new club.
            Uri newUri = getContentResolver().insert(ClubEntry.CONTENT_URI, values);

            // Insert the new row, returning the primary key value of the new row
            //long newRowId = db.insert(ClubEntry.TABLE_NAME, null, values);

            if (newUri == null){
                Toast.makeText(this, "Error with saving new club", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "New club saved", Toast.LENGTH_SHORT).show();
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
        public boolean onOptionsItemSelected(MenuItem item) {
            // User clicked on a menu option in the app bar overflow menu
            switch (item.getItemId()) {
                // Respond to a click on the "Save" menu option
                case R.id.action_save:
                    //Save a clubs to database
                    insertClub();
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
                    NavUtils.navigateUpFromSameTask(this);
                    return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }
