package com.example.juda.weathy;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.BindingBuildInfo;
import android.net.Uri;
import android.databinding.DataBindingUtil;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.juda.weathy.data.WeathyContract.WeathyEntry;

import com.example.juda.weathy.DateConvert;
import com.example.juda.weathy.WeatherConvert;
import com.example.juda.weathy.databinding.WeathySelectorBinding;

import static android.transition.Fade.IN;
import static com.example.juda.weathy.R.id.date;
import static com.example.juda.weathy.R.id.humidity;
import static com.example.juda.weathy.R.id.pressure;
import static com.example.juda.weathy.R.id.temp_high;
import static com.example.juda.weathy.R.id.temp_low;
import static com.example.juda.weathy.R.id.wind;

public class WeathySelector extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private String forecastDay;
    private Uri uri;
    private static final int DETAIL_LOADER_ID = 200;

    public static final String[] WEATHER_PROJECTION = {
            WeathyEntry.COLUMN_DATE,
            WeathyEntry.COLUMN_TEMP_MAX,
            WeathyEntry.COLUMN_TEMP_MIN,
            WeathyEntry.COLUMN_PRESSURE,
            WeathyEntry.COLUMN_HUMIDITY,
            WeathyEntry.COLUMN_WIND,
            WeathyEntry.COLUMN_DEGREES,
            WeathyEntry.COLUMN_WEATHY_ID
    };

    public static final int INDEX_DATE = 0;
    public static final int INDEX_TEMP_MAX = 1;
    public static final int INDEX_TEMP_MIN = 2;
    public static final int INDEX_PRESSURE = 3;
    public static final int INDEX_HUMIDITY = 4;
    public static final int INDEX_WIND = 5;
    public static final int INDEX_DEGREES = 6;
    public static final int INDEX_CONDITION = 7;

    private WeathySelectorBinding dataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.weathy_selector);

        uri = getIntent().getData();

        if (uri == null)
            throw new NullPointerException("URI can't be null");

        getSupportLoaderManager().initLoader(DETAIL_LOADER_ID, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.selector, menu);
        MenuItem menuItem = menu.findItem(R.id.slected_day_sharing);
        menuItem.setIntent(sharingForecastDay());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.settings){
            Intent openSettings = new Intent(this, Settings.class);
            startActivity(openSettings);
        }
        return super.onOptionsItemSelected(item);
    }

    private Intent sharingForecastDay() {
        Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setText(forecastDay)
                .getIntent();
        return shareIntent;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case DETAIL_LOADER_ID:
                return new CursorLoader(this,
                        uri, WEATHER_PROJECTION,
                        null, null, null);
            default:
                throw new RuntimeException("Loader didn't implement " + id);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        boolean hasCursorValidData = false;
        if (data != null && data.moveToFirst()) {
            hasCursorValidData = true;
        }
        if (!hasCursorValidData){
            return;
        }

        int weatherId = data.getInt(INDEX_CONDITION);
        int imageId = WeatherConvert.getLargeArtResourceIdForWeatherCondition(weatherId);
        dataBinding.primaryInfo.weatherIcon.setImageResource(imageId);

        long localDateGmt = data.getLong(INDEX_DATE);
        String dateString = DateConvert.dateConvertToString(this, localDateGmt, true);
        dataBinding.primaryInfo.date.setText(dateString);

        String descriptionString = WeatherConvert.getStringForWeatherCondition(this, weatherId);
        String descriptionAccess = getString(R.string.access_forecast, descriptionString);
        dataBinding.primaryInfo.description.setText(descriptionString);
        dataBinding.primaryInfo.description.setContentDescription(descriptionAccess);
        dataBinding.primaryInfo.weatherIcon.setContentDescription(descriptionAccess);

        double tempMax = data.getDouble(INDEX_TEMP_MAX);
        String tempHighString = WeatherConvert.formatTemp(this, tempMax);
        String highAccess = getString(R.string.access_temp_high, tempHighString);
        dataBinding.primaryInfo.tempHigh.setText(tempHighString);
        dataBinding.primaryInfo.tempHigh.setContentDescription(highAccess);

        double tempMin = data.getDouble(INDEX_TEMP_MIN);
        String tempLowString = WeatherConvert.formatTemp(this, tempMin);
        String lowAccess = getString(R.string.access_temp_low, tempLowString);
        dataBinding.primaryInfo.tempLow.setText(tempHighString);
        dataBinding.primaryInfo.tempLow.setContentDescription(lowAccess);

        float pressureFloat = data.getFloat(INDEX_PRESSURE);
        String pressureString = getString(R.string.format_pressure, pressureFloat);
        String pressureAccess = getString(R.string.access_pressure, pressureString);
        dataBinding.extraDetails.pressureMeasurement.setText(pressureString);
        dataBinding.extraDetails.pressureMeasurement.setContentDescription(pressureAccess);
        dataBinding.extraDetails.pressure.setContentDescription(pressureAccess);

        float humidityFloat = data.getFloat(INDEX_HUMIDITY);
        String humidityString = getString(R.string.format_humidity, humidityFloat);
        String humidityAcess = getString(R.string.access_humidity, humidityString);
        dataBinding.extraDetails.humidityMeasurement.setText(humidityString);
        dataBinding.extraDetails.humidityMeasurement.setContentDescription(humidityAcess);
        dataBinding.extraDetails.humidity.setContentDescription(humidityAcess);

        float windSpeed = data.getFloat(INDEX_WIND);
        float windDirection = data.getFloat(INDEX_DEGREES);
        String windString = WeatherConvert.getFormattedWindSpeed(this, windSpeed, windDirection);
        String windAccess = getString(R.string.access_wind, windString);
        dataBinding.extraDetails.windMeasurement.setText(windString);
        dataBinding.extraDetails.windMeasurement.setContentDescription(windAccess);
        dataBinding.extraDetails.wind.setContentDescription(windAccess);

        forecastDay = String.format("%s - %s - %s/%s", dateString, descriptionString,
                tempHighString, tempLowString);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
