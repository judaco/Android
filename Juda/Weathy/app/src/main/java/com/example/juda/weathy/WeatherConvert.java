package com.example.juda.weathy;

import android.content.Context;
import android.util.Log;

import com.example.juda.weathy.R;
import com.example.juda.weathy.data.WeathyPref;

/**
 * Created by Juda on 24/06/2017.
 */

public class WeatherConvert {

    private static double celsiusToFahrenheit(double temperatureInCelsius) {
        double temperatureInFahrenheit = (temperatureInCelsius * 1.8) + 32;
        return temperatureInFahrenheit;
    }

    public static String formatTemperature(Context context, double temperature) {
        int temperatureFormatResourceId = R.string.celcius_format;

        if (!WeathyPref.isMetric(context)) {
            temperature = celsiusToFahrenheit(temperature);
            temperatureFormatResourceId = R.string.fahrenheit_format;
        }
        return String.format(context.getString(temperatureFormatResourceId), temperature);
    }

    public static String formatHighAndLowTemperatures (Context context, double highTemp, double lowTemp) {
        long roundedHigh = Math.round(highTemp);
        long roundedLow = Math.round(lowTemp);

        String formattedHigh = formatTemperature(context, roundedHigh);
        String formattedLow = formatTemperature(context, roundedLow);

        String showHighLow = formattedHigh + " / " + formattedLow;
        return showHighLow;
    }
}
