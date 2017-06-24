package com.example.juda.weathy.data;

import android.content.Context;

/**
 * Created by Juda on 16/06/2017.
 */

public class WeathyPref {

    public static final String PREF_CITY_NAME = "city_name";
    public static final String PREF_LATITUDE = "meas_lat";
    public static final String PREF_LONGITUDE = "meas_lon";

    private static final String DEFAULT_WEATHER_LOCATION = "25035, Israel";
    private static final double [] DEFAULT_WEATHER_MEASURES = {31.7719, 35.2170};
    private static final String DEFAULT_MAP_LOCATION = "Derekh Rupin, Jerusalem, Israel";

    private static String getDefaultWeatherLocation(){
        return DEFAULT_WEATHER_LOCATION;
    }

    private static double [] getDefaultWeatherMeasures (){
        return DEFAULT_WEATHER_MEASURES;
    }

    static public void setLocDetails (Context context, String cityName, double lat, double lon){

    }

    static public void setLocation (Context context, String locSetting, double lat, double lon){

    }

    static public void resetLocMeasures (Context context){

    }

    public static String getPrefWeatherLocation (Context context){
        return getDefaultWeatherLocation();
    }

    public static boolean isMetric (Context context){
        return true;
    }

    public static double [] getLocMeasures (Context context){
        return getDefaultWeatherMeasures();
    }

    public static boolean isLocLatLonAvailalbe (Context context){
        return false;
    }
}