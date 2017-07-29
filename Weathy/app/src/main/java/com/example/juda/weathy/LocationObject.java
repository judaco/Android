package com.example.juda.weathy;

/**
 * Created by Juda on 28/07/2017.
 */

public class LocationObject {

    int id;
    String locCity;
    String weatherInfo;

    public LocationObject(int id, String locCity, String weatherInfo) {
        this.id = id;
        this.locCity = locCity;
        this.weatherInfo = weatherInfo;
    }

    public int getId() {
        return id;
    }

    public String getLocCity() {
        return locCity;
    }

    public String getWeatherInfo() {
        return weatherInfo;
    }
}
