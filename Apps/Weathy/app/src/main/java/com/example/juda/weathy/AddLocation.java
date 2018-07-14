package com.example.juda.weathy;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.juda.weathy.adapter_and_holders.WeathyArrayAdapter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by Juda on 05/08/2017.
 */

public class AddLocation extends AppCompatActivity {

    private static final String TAG = AddLocation.class.getSimpleName();

    private AutoCompleteTextView addLocation;
    private RequestQueue requestQueue;
    private WeathyArrayAdapter arrayAdapter;
    private static List <JsonObjectList> list;
    private WeathySharedPref sharedPref;
    private DatabaseQuery query;
    private DataFromSharedPref dataFromSp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);
        setTitle(Assistant.LOCATION_MANAGER);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        sharedPref = new WeathySharedPref(AddLocation.this);
        query = new DatabaseQuery(AddLocation.this);
        requestQueue = Volley.newRequestQueue(this);
        addLocation = (AutoCompleteTextView)findViewById(R.id.new_location);
        Button goToLocBtn = (Button)findViewById(R.id.go_to_location_button);
        goToLocBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddLocation.this, ListLocation.class);
                startActivity(intent);
            }
        });
        final Button addLocBtn = (Button)findViewById(R.id.add_location_button);
        addLocBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String locationEntered = addLocation.getText().toString();
                if (TextUtils.isEmpty(locationEntered)) {
                    Toast.makeText(AddLocation.this, Assistant.LOCATION_ERROR_MSG, Toast.LENGTH_SHORT).show();
                    return;
                }
                //database
                int numOfSavedLocations = query.countStoredLocations();
                Toast.makeText(AddLocation.this, "Total locations saved " + numOfSavedLocations, Toast.LENGTH_SHORT).show();
                if (numOfSavedLocations <= 8) {
                    query.addNewLocation(locationEntered);
                } else {
                    Toast.makeText(AddLocation.this, getString(R.string.location_stored), Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(AddLocation.this, ListLocation.class);
                startActivity(intent);
            }
        });
    }
    private class DataFromSharedPref extends AsyncTask<Void, Void, List<JsonObjectList>> {

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(List<JsonObjectList> jsonObjectLists) {
            if (jsonObjectLists != null) {
                list = jsonObjectLists;
                arrayAdapter = new WeathyArrayAdapter(AddLocation.this, R.layout.city_list, list);
                addLocation.setAdapter(arrayAdapter);
                addLocation.setThreshold(1);
            }
        }

        @Override
        protected List<JsonObjectList> doInBackground(Void... voids) {
            return mergeStoredData();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (list != null) {
            dataFromSp = new DataFromSharedPref();
            dataFromSp.execute();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != null) {
            dataFromSp.cancel(true);
        }
    }
    private List<JsonObjectList> mergeStoredData(){
        List<JsonObjectList> object1 = sharedPref.getAllDataObject(Assistant.FIRST_DATA_STORED);
        List<JsonObjectList> object2 = sharedPref.getAllDataObject(Assistant.SECOND_DATA_STORED);
        return Lists.newArrayList(Iterables.concat(object1, object2));
    }
}
