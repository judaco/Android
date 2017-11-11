package com.example.juda.weathy;

import android.Manifest;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.juda.weathy.adapter_and_holders.RecyclerViewAdapter;
import com.example.juda.weathy.json.FiveDaysForecast;
import com.example.juda.weathy.json.FiveWeathers;
import com.example.juda.weathy.json.Forecast;
import com.example.juda.weathy.json.LocationMapObject;
import com.github.pavlospt.CircleView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * Created by Juda on 11/08/2017.
 */

public class WeathyActivity extends AppCompatActivity implements LocationListener {

    private static final String TAG = WeathyActivity.class.getSimpleName();
    private String apiUrl;
    private String isLocationSaved;
    private DatabaseQuery query;
    private RequestQueue queue;
    private RecyclerViewAdapter viewAdapter;
    private WeathySharedPref sharedPref;
    private FiveDaysForecast fiveDaysForecast;

    private final int REQUEST_LOC = 200;
    private Location location;
    private LocationMapObject locationMapObject;
    private LocationManager locationManager;

    private RecyclerView recyclerView;
    private TextView cityCountry;
    private TextView currentDate;
    private TextView wind;
    private TextView humidity;
    private CircleView circleView;
    private ImageView weatherIcon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weathy);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        queue = Volley.newRequestQueue(this);
        query = new DatabaseQuery(WeathyActivity.this);
        sharedPref = new WeathySharedPref(WeathyActivity.this);
        isLocationSaved = sharedPref.getLocationInPref();

        cityCountry = (TextView) findViewById(R.id.city_country);
        currentDate = (TextView) findViewById(R.id.current_date);
        wind = (TextView) findViewById(R.id.wind_result);
        humidity = (TextView) findViewById(R.id.humidity_result);
        circleView = (CircleView) findViewById(R.id.weather_result);
        weatherIcon = (ImageView) findViewById(R.id.weather_icon);

        locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(WeathyActivity.this, new String[]{Manifest.permission
                    .ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOC);
        } else {
            if (isLocationSaved.equals("")) {
                //Make API call with longitude and latitude
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 2, this);
                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    apiUrl = "http://api.openweathermap.org/data/2.5/weather?lat=" + location.getLatitude() + "&lon=" + location.getLongitude() + "&APPID=8fc357cff194885b910409cd0842f93a";
                    createJsonObject(apiUrl);
                }
            } else {
                //Make API call with a city name
                String storedCityName = sharedPref.getLocationInPref();
                System.out.println("Stored city " + storedCityName);
                String[] city = storedCityName.split(",");
                if (TextUtils.isEmpty(city[0])) {
                    System.out.println("Stored city " + city[0]);
                    String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city[0] + "&APPID=8fc357cff194885b910409cd0842f93a";
                    createJsonObject(url);
                }
            }
        }

        ImageButton addLocation = (ImageButton)findViewById(R.id.add_location);
        addLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addLocIntent = new Intent(WeathyActivity.this, AddLocation.class);
                startActivity(addLocIntent);
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(WeathyActivity.this, 4);
        recyclerView = (RecyclerView)findViewById(R.id.weather_daily_list);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
    }

    private void createJsonObject (final String apiUrl) {
        StringRequest request = new StringRequest(Request.Method.GET, apiUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Responde " + response);
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                locationMapObject = gson.fromJson(response, LocationMapObject.class);
                if (locationMapObject == null) {
                    Toast.makeText(getApplicationContext(), "Nothing returned", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Good Response", Toast.LENGTH_SHORT).show();
                    String city = locationMapObject.getName() + ", " + locationMapObject.getSys().getCountry();
                    String dateOfToday = getDateOfTodayInString();
                    Long temperature = Math.round(Math.floor(Double.parseDouble(locationMapObject.getMain().getTemp())));
                    String weatherTemp = String.valueOf(temperature);
                    String description = Assistant.firstLetterCapitalization(locationMapObject.getWeather().get(0).getDescription());
                    String windSpeed = locationMapObject.getWind().getSpeed();
                    String humidityValue = locationMapObject.getMain().getHumidity();

                    //Saving the location into the Database
                    if (apiUrl.contains("lat")) {
                        query.addNewLocation(locationMapObject.getName());
                    }
                    //The view data
                    cityCountry.setText(Html.fromHtml(city));
                    currentDate.setText(Html.fromHtml(dateOfToday));
                    circleView.setTitleText(Html.fromHtml(weatherTemp).toString());
                    circleView.setSubtitleText(Html.fromHtml(description).toString());
                    wind.setText(Html.fromHtml(windSpeed) + " km/h");
                    humidity.setText(Html.fromHtml(humidityValue) + " %");

                    fiveDaysApiJsonObjectCall(locationMapObject.getName());

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_LOC) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    //make api call
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 2, this);
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        apiUrl = "http://api.openweathermap.org/data/2.5/weather?lat="+location.getLatitude()+"&lon="+location.getLongitude()+"&APPID=8fc357cff194885b910409cd0842f93a";
                        createJsonObject(apiUrl);
                    }else{
                        apiUrl = "http://api.openweathermap.org/data/2.5/weather?lat=35.000&lon=32.000&APPID=8fc357cff194885b910409cd0842f93a";
                        createJsonObject(apiUrl);
                    }
                }
            }else{
                Toast.makeText(WeathyActivity.this, getString(R.string.permission_notification), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {
        if (s.equals(LocationManager.GPS_PROVIDER)) {
            showGPSAlert();
        }
    }

    private void showGPSAlert() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS is disabled in you device. Please enable the GPS").setCancelable(false)
                .setPositiveButton("Go to Settings in order to enable the GPS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent gpsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(gpsIntent);
                    }
                });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private String getDateOfTodayInString() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("E, d MMMM", Locale.getDefault());
        return dateFormat.format(calendar.getTime());
    }

    private void fiveDaysApiJsonObjectCall(String city) {
        String apiUrl = "http://api.openweathermap.org/data/2.5/forecast?q="+city+ "&APPID=8fc357cff194885b910409cd0842f93a";
        final List<WeathyObject> weekDays = new ArrayList<WeathyObject>();
        StringRequest request = new StringRequest(Request.Method.GET, apiUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, "5 days response " + response);
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                Forecast forecast = gson.fromJson(response, Forecast.class);
                if (forecast == null) {
                    Toast.makeText(getApplicationContext(), "Nothing returned", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Good response", Toast.LENGTH_SHORT).show();

                    int[] everyDay = new int[]{0, 0, 0, 0, 0, 0, 0};

                    List<FiveWeathers> weathersList = forecast.getList();
                    if (weathersList != null) {
                        for (int i = 0; i < weathersList.size(); i++) {
                            String time = weathersList.get(i).getDt_txt();
                            String shortDay = convertTimetoDay(time);
                            String temperature = weathersList.get(i).getMain().getTemp();
                            String minTemp = weathersList.get(i).getMain().getTemp_min();

                            if (convertTimetoDay(time).equals("Monday") && everyDay[0] < 1) {
                                weekDays.add(new WeathyObject(shortDay, R.drawable.clear_day, temperature, minTemp));
                                everyDay[0] = 1;
                            }
                            if (convertTimetoDay(time).equals("Tuesday") && everyDay[1] < 1) {
                                weekDays.add(new WeathyObject(shortDay, R.drawable.clear_day, temperature, minTemp));
                                everyDay[1] = 1;
                            }
                            if (convertTimetoDay(time).equals("Wednesday") && everyDay[2] < 1) {
                                weekDays.add(new WeathyObject(shortDay, R.drawable.clear_day, temperature, minTemp));
                                everyDay[2] = 1;
                            }
                            if (convertTimetoDay(time).equals("Thursday") && everyDay[3] < 1) {
                                weekDays.add(new WeathyObject(shortDay, R.drawable.clear_day, temperature, minTemp));
                                everyDay[3] = 1;
                            }
                            if (convertTimetoDay(time).equals("Friday") && everyDay[4] < 1) {
                                weekDays.add(new WeathyObject(shortDay, R.drawable.clear_day, temperature, minTemp));
                                everyDay[4] = 1;
                            }
                            if (convertTimetoDay(time).equals("Saturday") && everyDay[5] < 1) {
                                weekDays.add(new WeathyObject(shortDay, R.drawable.clear_day, temperature, minTemp));
                                everyDay[5] = 1;
                            }
                            if (convertTimetoDay(time).equals("Sunday") && everyDay[6] < 1) {
                                weekDays.add(new WeathyObject(shortDay, R.drawable.clear_day, temperature, minTemp));
                                everyDay[6] = 1;
                            }
                            viewAdapter = new RecyclerViewAdapter(WeathyActivity.this, weekDays);
                            recyclerView.setAdapter(viewAdapter);
                        }
                    }
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

    private String convertTimetoDay(String time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:SSSS", Locale.getDefault());
        String days = "";
        try {
            Date date = simpleDateFormat.parse(time);
            System.out.println("Local time " + date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            days = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());
            System.out.println("Local time " + days);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }
}
