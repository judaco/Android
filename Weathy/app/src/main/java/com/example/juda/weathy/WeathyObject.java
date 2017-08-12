package com.example.juda.weathy;

/**
 * Created by Juda on 05/08/2017.
 */

public class WeathyObject {

    private String day;
    private int weatherIcon;
    private String weatherResult;
    private String smallWeatherResult;

    public WeathyObject(String day, int weatherIcon, String weatherResult, String smallWeatherResult) {
        this.day = day;
        this.weatherIcon = weatherIcon;
        this.weatherResult = weatherResult;
        this.smallWeatherResult = smallWeatherResult;
    }

    public String getDay() {
        return day;
    }

    public int getWeatherIcon() {
        return weatherIcon;
    }

    public String getWeatherResult() {
        return weatherResult;
    }

    public String getSmallWeatherResult() {
        return smallWeatherResult;
    }
}
