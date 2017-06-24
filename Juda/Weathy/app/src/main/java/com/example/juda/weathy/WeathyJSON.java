package com.example.juda.weathy;

import android.content.ContentValues;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.HttpURLConnection;

/**
 * Created by Juda on 24/06/2017.
 */

public class WeathyJSON {

    public static String [] getStringsFromJson (Context context, String jsonForecastString)
        throws JSONException{
        final String W_LIST = "list";

        final String W_TEMPERATURE = "temp";

        final String W_MAX = "max";
        final String W_MIN = "min";

        final String W_WEATHER = "weather";
        final String W_DESCRIPTION = "main";

        final String W_MESSAGE_CODE = "cod";

        JSONObject forecastJson = new JSONObject(jsonForecastString);

        String [] dataWeatherHolder = null;

        //Error checking
        if (forecastJson.has(W_MESSAGE_CODE)){
            int error = forecastJson.getInt(W_MESSAGE_CODE);

            switch (error) {
                case HttpURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    return null;
                case HttpURLConnection.HTTP_BAD_REQUEST:
                    return null;
                case HttpURLConnection.HTTP_BAD_GATEWAY:
                    return null;
                default:
                    return null;
            }
        }

        JSONArray weatherArray = forecastJson.getJSONArray(W_LIST);
        dataWeatherHolder = new String[weatherArray.length()];

        long localDate = System.currentTimeMillis();
        long UTCDate = DateConvert.getUTCDateFromLocal(localDate);
        long startingDay = DateConvert.normalDate(UTCDate);

        for (int i = 0; i < weatherArray.length(); i++) {
            String date;
            String highAndLow;
            long dateTimeInMilliSec;
            double highTemp;
            double lowTemp;
            String descirption;

            //JSON object who will show the day
            JSONObject forecastDay = weatherArray.getJSONObject(i);

            dateTimeInMilliSec = startingDay + DateConvert.DAY_IN_MILLISEC * i;
            date = DateConvert.dateString(context, dateTimeInMilliSec, false);

            JSONObject weatherObject = forecastDay.getJSONArray(W_WEATHER).getJSONObject(0);
            descirption = weatherObject.getString(W_DESCRIPTION);

            JSONObject tempObject = forecastDay.getJSONObject(W_TEMPERATURE);
            highTemp = tempObject.getDouble(W_MAX);
            lowTemp = tempObject.getDouble(W_MIN);
            highAndLow = WeatherConvert.formatHighAndLowTemperatures(context, highTemp, lowTemp);

            dataWeatherHolder[i] = date + " - " + descirption + " - " + highAndLow;
        }
        return dataWeatherHolder;
    }

    public static ContentValues [] getFullDataFromJson (Context context, String jsonForecastString) {
        return null;
    }
}
