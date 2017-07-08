package com.example.juda.weathy;

import android.database.Cursor;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.juda.weathy.data.WeathyPref;
import com.example.juda.weathy.data.WeathyContract.WeathyEntry;

public class MainActivity extends AppCompatActivity implements
        LoaderCallbacks<Cursor>, WeathyAdapter.WeathyAdapterOnClickHandler {

    private RecyclerView recyclerView;
    private int position = RecyclerView.NO_POSITION;
    private ProgressBar progressBar;
    private WeathyAdapter weathyAdapter;

    private static final int FORECAST_LOADER_ID = 50;

    public static final String [] MAIN_FORECAST_PROJECTION = {
            WeathyEntry.COLUMN_DATE,
            WeathyEntry.COLUMN_TEMP_MAX,
            WeathyEntry.COLUMN_TEMP_MIN,
            WeathyEntry.COLUMN_WEATHY_ID,
    };

    public static final int INDEX_WEATHER_DATE = 0;
    public static final int INDEX_WEATHER_TEMP_MAX = 1;
    public static final int INDEX_WEATHER_TEMP_MIN = 2;
    public static final int INDEX_WEATHER_CONDITION_ID = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar)findViewById(R.id.progress_bar_loading);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView_weathy);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        weathyAdapter = new WeathyAdapter(this, this);
        recyclerView.setAdapter(weathyAdapter);

        showLoading();

        Bundle bundle = null;
        getSupportLoaderManager().initLoader(FORECAST_LOADER_ID, bundle, this);
    }

    private void loadLocationMap(){
        double [] coordinates = WeathyPref.getLocCoords(this);
        String latPos = Double.toString(coordinates[0]);
        String lonPos = Double.toString(coordinates[1]);
        Uri geoLocation = Uri.parse("geo:" + latPos + "," + lonPos);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.e("MainActivity", "couldn't find your place");
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case FORECAST_LOADER_ID:
                Uri forecastQueryUri = WeathyEntry.CONTENT_URI;
                String sortOrder = WeathyEntry.COLUMN_DATE + " ASC";
                String selection = WeathyEntry.getSqlSelectorForToday();

                return new CursorLoader(this, forecastQueryUri, MAIN_FORECAST_PROJECTION,
                        selection, null, sortOrder);
            default:
                throw new RuntimeException("Loader didn't implement " + id);
    }
}

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        progressBar.setVisibility(View.INVISIBLE);
        weathyAdapter.swapCursor(data);
        if (position == RecyclerView.NO_POSITION) {
            position = 0;
        }
        recyclerView.smoothScrollToPosition(position);
        if (data.getCount() != 0){
            weatherDataView();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        weathyAdapter.swapCursor(null);
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
        progressBar.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void showLoading() {
        recyclerView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
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
//        if (itemId == R.id.refresh){
//                invalidateData();
//            getSupportLoaderManager().restartLoader(FORECAST_LOADER_ID, null, this);
//            return true;
//        }

        if (itemId == R.id.map){
            loadLocationMap();
            return true;
        }

        if (itemId == R.id.settings){
            Intent openSettings = new Intent(this, Settings.class);
            startActivity(openSettings);
        }
        return super.onOptionsItemSelected(item);
    }
}
