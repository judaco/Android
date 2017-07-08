package com.example.juda.weathy;

import android.content.ContentValues;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.HttpURLConnection;

import com.example.juda.weathy.data.WeathyContract.WeathyEntry;
import com.example.juda.weathy.data.WeathyPref;


/**
 * Created by Juda on 24/06/2017.
 */

public final class WeathyJSON {

    private static final String W_CITY = "city_name";
    private static final String W_COORDINATE = "coord";

    private static final String W_LATITUDE = "lat";
    private static final String W_LONGTITUDE = "lon";

    private static final String W_LIST = "list";

    private static final String W_PRESSURE = "pressure";
    private static final String W_HUMIDITY = "humidity";
    private static final String W_WIND = "wind_speed";
    private static final String W_WIND_DIRECTION = "degree";

    private static final String W_TEMP = "temp";
    private static final String W_TEMP_MAX = "max";
    private static final String W_TEMP_MIN = "min";

    private static final String W_WEATHER = "weather";
    private static final String W_WEATHER_ID = "id";
    private static final String W_MSG_CODE = "cod";

    public static String [] getStringsFromJson (Context context, String jsonForecastString)
        throws JSONException{

        String [] dataWeatherHolder = null;

        JSONObject jsonForecast = new JSONObject(jsonForecastString);

        //Error checking
        if (jsonForecast.has(W_MSG_CODE)){
            int error = jsonForecast.getInt(W_MSG_CODE);

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

        JSONArray weatherArray = jsonForecast.getJSONArray(W_LIST);
        dataWeatherHolder = new String[weatherArray.length()];

        long startingDay = DateConvert.getNormalizedUtcDateForToday();

        for (int i = 0; i < weatherArray.length(); i++) {
            String date;
            String highAndLow;
            long dateTimeInMilliSec;
            double highTemp;
            double lowTemp;

            int weatherId;
            String description;

            //JSON object who will show the day
            JSONObject forecastDay = weatherArray.getJSONObject(i);

            dateTimeInMilliSec = startingDay + DateConvert.DAY_IN_MILLIS * i;
            date = DateConvert.getFriendlyDateString(context, dateTimeInMilliSec, false);

            JSONObject weatherObject = forecastDay.getJSONArray(W_WEATHER).getJSONObject(0);
            weatherId = weatherObject.getInt(W_WEATHER_ID);
            description = WeatherConvert.getStringForWeatherCondition(context, weatherId);

            JSONObject tempObject = forecastDay.getJSONObject(W_TEMP);
            highTemp = tempObject.getDouble(W_TEMP_MAX);
            lowTemp = tempObject.getDouble(W_TEMP_MIN);
            highAndLow = WeatherConvert.formatHighAndLowTemperatures(context, highTemp, lowTemp);

            dataWeatherHolder[i] = date + " - " + description + " - " + highAndLow;
        }
        return dataWeatherHolder;
    }

    public static ContentValues [] getFullDataFromJson (Context context, String jsonForecastString)
    throws JSONException {

        JSONObject jsonForecast = new JSONObject(jsonForecastString);

        //Error checking
        if (jsonForecast.has(W_MSG_CODE)){
            int error = jsonForecast.getInt(W_MSG_CODE);

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

        JSONArray jsonArray = jsonForecast.getJSONArray(W_LIST);

        JSONObject jsonCity = jsonForecast.getJSONObject(W_CITY);
        JSONObject cityCoord = jsonCity.getJSONObject(W_COORDINATE);

        double cityLatitude = cityCoord.getDouble(W_LATITUDE);
        double cityLongitude = cityCoord.getDouble(W_LONGTITUDE);

        WeathyPref.setLocDetails(context, W_CITY, cityLatitude, cityLongitude);

        ContentValues [] contentValues = new ContentValues[jsonArray.length()];

        long normalUTCStartDay = DateConvert.getNormalizedUtcDateForToday();

        for (int i = 0; i < jsonArray.length(); i++) {

            long dateTimeInMillis;
            double high;
            double low;
            double windSpeed;
            double windDirection;
            double pressure;
            int weatherId;
            int humidity;

            JSONObject forecastDay = jsonArray.getJSONObject(i);

            dateTimeInMillis = normalUTCStartDay + DateConvert.DAY_IN_MILLIS * i;

            windSpeed = forecastDay.getDouble(W_WIND);
            windDirection = forecastDay.getDouble(W_WIND_DIRECTION);
            pressure = forecastDay.getDouble(W_PRESSURE);
            humidity = forecastDay.getInt(W_HUMIDITY);

            JSONObject weatherObject = forecastDay.getJSONArray(W_WEATHER).getJSONObject(0);
            weatherId = weatherObject.getInt(W_WEATHER_ID);

            JSONObject tempObject = forecastDay.getJSONObject(W_TEMP);
            high = tempObject.getDouble(W_TEMP_MAX);
            low = tempObject.getDouble(W_TEMP_MIN);

            ContentValues weatherValues = new ContentValues();
            weatherValues.put(WeathyEntry.COLUMN_DATE, dateTimeInMillis);
            weatherValues.put(WeathyEntry.COLUMN_TEMP_MAX, high);
            weatherValues.put(WeathyEntry.COLUMN_TEMP_MIN, low);
            weatherValues.put(WeathyEntry.COLUMN_WIND, windSpeed);
            weatherValues.put(WeathyEntry.COLUMN_DEGREES, windDirection);
            weatherValues.put(WeathyEntry.COLUMN_PRESSURE, pressure);
            weatherValues.put(WeathyEntry.COLUMN_WEATHY_ID, weatherId);
            weatherValues.put(WeathyEntry.COLUMN_HUMIDITY, humidity);

            contentValues[i] = weatherValues;
        }
        return contentValues;
    }
}
