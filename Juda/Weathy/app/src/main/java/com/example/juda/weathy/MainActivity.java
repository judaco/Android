package com.example.juda.weathy;

import android.content.SharedPreferences;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.Loader;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juda.weathy.data.WeathyPref;
import com.example.juda.weathy.NetworkConnection;
import com.example.juda.weathy.WeathyJSON;

import org.json.JSONException;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements WeathyAdapter.WeathyAdapterOnClickItem,
        LoaderCallbacks<String[]>, SharedPreferences.OnSharedPreferenceChangeListener{

    private RecyclerView recyclerView;
    private TextView errorMessage;
    private ProgressBar progressBar;
    private WeathyAdapter weathyAdapter;

    private static final int FORECAST_LOADER_ID = 0;

    private static boolean PREFERENCES_UPDATED = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        errorMessage = (TextView)findViewById(R.id.error_message);
        progressBar = (ProgressBar)findViewById(R.id.progress_bar_loading);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView_weathy);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        weathyAdapter = new WeathyAdapter(this);
        recyclerView.setAdapter(weathyAdapter);

        int loaderId = FORECAST_LOADER_ID;

        android.support.v4.app.LoaderManager.LoaderCallbacks<String[]> callbacks = (LoaderCallbacks<String[]>) MainActivity.this;
        Bundle bundle = null;
        getSupportLoaderManager().initLoader(loaderId, bundle, callbacks);

        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public Loader<String[]> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<String[]>(this) {

            //Hold and cache our weather data
            String[] weatherData = null;

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                if (weatherData != null) {
                    deliverResult(weatherData);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    forceLoad();
                }
            }

            @Override
            public String[] loadInBackground() {

                String loc = WeathyPref.getPrefWeatherLocation(MainActivity.this);

                URL requestUrl = NetworkConnection.url(loc);

                try {
                    String jsonResponse = NetworkConnection.getResponseFromHTTPUrl(requestUrl);

                    String[] jsonData = WeathyJSON.getStringsFromJson(MainActivity.this, jsonResponse);

                    return jsonData;

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                    return null;
                }
                return new String[0];
            }

            public void deliverResult(String[] data) {
                weatherData = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<String[]> loader, String [] data) {

        progressBar.setVisibility(View.INVISIBLE);
        weathyAdapter.setWeatherData(data);
        if (data == null) {
            showErrorMessage();
        } else {
            weatherDataView();
        }
    }

    @Override
    public void onLoaderReset(Loader<String[]> loader) {

    }

    private void invalidateData() {
        weathyAdapter.setWeatherData(null);
    }

    private void loadLocationMap(){
        String loc = WeathyPref.getPrefWeatherLocation(this);
        Uri geoLocation = Uri.parse("geo:0,0?q=" + loc);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.e("MainActivity", "couldn't find your place");
        }
    }

    @Override
    public void onClick(String weatherDay) {
        Context context = this;
        Class moveTo = WeathySelector.class;
        Intent intent = new Intent(context, moveTo);
        intent.putExtra(Intent.EXTRA_TEXT, weatherDay);
        startActivity(intent);
    }

    private void weatherDataView(){
        errorMessage.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage(){
        recyclerView.setVisibility(View.INVISIBLE);
        errorMessage.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (PREFERENCES_UPDATED) {
            Toast.makeText(this, "preferences has been updated", Toast.LENGTH_SHORT).show();
            getSupportLoaderManager().restartLoader(FORECAST_LOADER_ID, null, this);
            PREFERENCES_UPDATED = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.forecast, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        //switch (itemId){
            //case itemId == R.id.menu_search:
               // Toast.makeText(this, "Search clicked!", Toast.LENGTH_SHORT).show();
              //  break;
        if (itemId == R.id.refresh){
                invalidateData();
            getSupportLoaderManager().restartLoader(FORECAST_LOADER_ID, null, this);
            return true;
        }

        if (itemId == R.id.map){
            weathyAdapter.setWeatherData(null);
            loadLocationMap();
            return true;
        }

        if (itemId == R.id.settings){
            Intent openSettings = new Intent(this, Settings.class);
            startActivity(openSettings);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        PREFERENCES_UPDATED = true;
    }
}
