package com.example.juda.weathy;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import java.net.URL;

import static android.R.attr.id;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static android.os.Build.VERSION_CODES.N;
import static com.example.juda.weathy.R.id.weather_data;

public class MainActivity extends AppCompatActivity implements WeathyAdapter.WeathyAdapterOnClickItem{

    private RecyclerView recyclerView;
    private TextView errorMessage;
    private ProgressBar progressBar;
    private WeathyAdapter weathyAdapter;

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

        loadWeatherData();
    }

    private void loadWeatherData(){
        weatherDataView();
        String loc = WeathyPref.getPrefWeatherLocation(this);
        new WeatherDataTask().execute(loc);
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
    public void onClick(String weatherDay) {
        Context context = this;
        Toast.makeText(context, weatherDay, Toast.LENGTH_SHORT).show();
    }

    public class WeatherDataTask extends AsyncTask <String, Void, String []> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String[] doInBackground(String... params) {

            if (params.length == 0){
                return null;
            }

            String loc = params[0];
            URL requestUrl = NetworkConnection.url(loc);

            try {
                String jsonResponse = NetworkConnection.getResponseFromHTTPUrl(requestUrl);

                String [] jsonData = WeathyJSON.getStringsFromJson(MainActivity.this, jsonResponse);

                return jsonData;

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String[] weatherData) {
            progressBar.setVisibility(View.INVISIBLE);
            if (weatherData != null) {
                weatherDataView();
                weathyAdapter.setWeatherData(weatherData);
            } else {
                showErrorMessage();
            }
        }
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
                weathyAdapter.setWeatherData(null);
                loadWeatherData();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
