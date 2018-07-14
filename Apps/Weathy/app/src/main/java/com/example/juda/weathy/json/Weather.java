package com.example.juda.weathy.json;

/**
 * Created by Juda on 06/08/2017.
 */

public class Weather {

    WeatherResults weatherResults;

    public Weather(WeatherResults weatherResults) {
        this.weatherResults = weatherResults;
    }

    public WeatherResults getWeatherResults() {
        return weatherResults;
    }
}
