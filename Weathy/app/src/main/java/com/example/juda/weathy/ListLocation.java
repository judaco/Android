package com.example.juda.weathy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.juda.weathy.adapter_and_holders.LocationAdapter;
import com.example.juda.weathy.json.LocationMapObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juda on 05/08/2017.
 */

public class ListLocation extends AppCompatActivity{

    private static final String TAG = ListLocation.class.getSimpleName();
    private RecyclerView locRecView;
    private LocationAdapter locationAdapter;
    private RequestQueue queue;
    private List<DatabaseLocationObject> allLocations;
    private List<LocationObject> allData;
    private LocationObject locationObject;
    private DatabaseQuery query;
    private LocationMapObject locationMapObject;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_location);
        setTitle(Assistant.LOCATION_LIST);
        queue = Volley.newRequestQueue(ListLocation.this);
        allData = new ArrayList<LocationObject>();
        query = new DatabaseQuery(ListLocation.this);
        allLocations = query.getStoredDataLocations();
        if (allLocations != null) {
            for (int i = 0; i < allLocations.size(); i++) {
                //Volley network call
                System.out.println("Response is " + allLocations.get(i).getLocation());
                requestJsonObj(allLocations.get(i));
            }
        }
        Toast.makeText(ListLocation.this, "Number of locations is" + allLocations.size(), Toast.LENGTH_SHORT).show();
        ImageButton addLocation = (ImageButton)findViewById(R.id.add_location);
        addLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addLocIntent = new Intent(ListLocation.this, AddLocation.class);
                startActivity(addLocIntent);
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(ListLocation.this);
        locRecView = (RecyclerView)findViewById(R.id.list_location);
        locRecView.setLayoutManager(layoutManager);
    }

    private void requestJsonObj(final  DatabaseLocationObject param) {
        String url =  "http://api.openweathermap.org/data/2.5/weather?q="+param.getLocation()
                +"&APPID=8fc357cff194885b910409cd0842f93a";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Response " + response);
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                locationMapObject = gson.fromJson(response, LocationMapObject.class);
                if (locationMapObject == null) {
                    Toast.makeText(getApplicationContext(), "Nothing returned", Toast.LENGTH_SHORT).show();
                } else {
                    int rowId = param.getId();
                    Long tempValue = Math.round(Math.floor(Double.parseDouble(locationMapObject.getMain().getTemp())));
                    String city = locationMapObject.getName() + ", " + locationMapObject.getSys().getCountry();
                    String weatherInfo = String.valueOf(tempValue) + "<sup>o</sup>, "
                            + Assistant.firstLetterCapitalization(locationMapObject.getWeather().get(0).getDescription());
                    allData.add(new LocationObject(rowId, city, weatherInfo));
                    locationAdapter = new LocationAdapter(ListLocation.this, allData);
                    locRecView.setAdapter(locationAdapter);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error " + error.getMessage());
            }
          });
        queue.add(request);
    }
}
