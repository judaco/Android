package com.example.juda.weathy.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

import com.example.juda.weathy.R;

/**
 * Created by Juda on 16/06/2017.
 */

public class WeathyPref {

    public static final String PREF_CITY_NAME = "city_name";
    public static final String PREF_LATITUDE = "meas_lat";
    public static final String PREF_LONGITUDE = "meas_lon";

    private static final String DEFAULT_WEATHER_LOCATION = "25035, Israel";
    private static final double [] DEFAULT_WEATHER_COORDINATES = {31.7719, 35.2170};
    private static final String DEFAULT_MAP_LOCATION = "Derekh Rupin, Jerusalem, Israel";

    private static String getDefaultWeatherLocation(){
        return DEFAULT_WEATHER_LOCATION;
    }

    private static double [] getDefaultWeatherCoordinates (){
        return DEFAULT_WEATHER_COORDINATES;
    }

    static public void setLocDetails (Context context, String cityName, double lat, double lon){

    }

    static public void setLocation (Context context, String locSetting, double lat, double lon){

    }

    static public void resetLocCoordinates (Context context){

    }

    public static String getPrefWeatherLocation (Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String locationKey = context.getString(R.string.location_key);
        String locationDefault = context.getString(R.string.location_default);
        return preferences.getString(locationKey, locationDefault);
    }

    public static boolean isMetric (Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String unitsKey = context.getString(R.string.units_key);
        String unitsDefault = context.getString(R.string.units_metric);
        String unitsPref = preferences.getString(unitsKey, unitsDefault);
        String metric = context.getString(R.string.units_metric);

        boolean userPrefMetric;
        if (metric.equals(unitsPref)) {
            userPrefMetric = true;
        } else {
            userPrefMetric = false;
        }
        return userPrefMetric;
    }

    public static double [] getLocCoords (Context context){
        return getDefaultWeatherCoordinates();
    }

    public static boolean isLocLatLonAvailialbe (Context context){
        return false;
    }
}