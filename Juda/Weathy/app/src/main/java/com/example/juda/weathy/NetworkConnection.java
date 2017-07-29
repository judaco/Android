package com.example.juda.weathy;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.example.juda.weathy.data.WeathyPref;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import static android.content.ContentValues.TAG;

/**
 * Created by Juda on 24/06/2017.
 */

public final class NetworkConnection {

    private static final String TAG = NetworkConnection.class.getSimpleName();

    private static final String DYNAMIC_URL = "http://api.openweathermap.org/data/2.5/weather?q=%s&units=metric";
    private static final String FORECAST_URL = DYNAMIC_URL;

    private static final String format = "json";
    private static final String units = "metric";
    private static final int numDays = 10;

    final static String QUERY = "query";
    final static String LATITUDE = "lat";
    final static String LONGITUDE = "lon";
    final static String FORMAT = "mode";
    final static String UNITS = "units";
    final static String DAYS = "cnt";

    public static URL getUrl(Context context) {
        if (WeathyPref.isLocLatLonAvailialbe(context)) {
            double [] prefCoordinates = WeathyPref.getLocCoords(context);
            double lat = prefCoordinates[0];
            double lon = prefCoordinates[1];
            return urlLatLonBuilder(lat, lon);
        } else {
            String locQuery = WeathyPref.getPrefWeatherLocation(context);
            return urlLocQueryBuilder(locQuery);
        }
    }

    private static URL urlLatLonBuilder (Double lat, Double lon) {
        Uri queryUri = Uri.parse(FORECAST_URL).buildUpon()
                .appendQueryParameter(LATITUDE, String.valueOf(lat))
                .appendQueryParameter(LONGITUDE, String.valueOf(lon))
                .appendQueryParameter(FORMAT, format)
                .appendQueryParameter(UNITS, units)
                .appendQueryParameter(DAYS, Integer.toString(numDays)).build();
        try {
            URL queryUrl = new URL(queryUri.toString());
            Log.d(TAG, "URL: " + queryUrl);
            return queryUrl;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static URL urlLocQueryBuilder (String locQuery) {
        Uri queryUri = Uri.parse(FORECAST_URL).buildUpon()
                .appendQueryParameter(QUERY, locQuery )
                .appendQueryParameter(FORMAT, format)
                .appendQueryParameter(UNITS, units)
                .appendQueryParameter(DAYS, Integer.toString(numDays)).build();
        try {
            URL queryUrl = new URL(queryUri.toString());
            Log.d(TAG, "URL: " + queryUrl);
            return queryUrl;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getResponseFromHTTPUrl (URL url) throws IOException{
        HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
        try {
            InputStream inputStream = httpURLConnection.getInputStream();

            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            String response = null;
            if (hasInput) {
                response = scanner.next();
            }
            scanner.close();
            return response;
        } finally {
            httpURLConnection.disconnect();
        }
    }
}
