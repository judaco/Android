package com.example.juda.weathy.json;

/**
 * Created by Juda on 06/08/2017.
 */

public class Main {

    private String temp;
    private String pressure;
    private String humidity;
    private String temp_min;
    private String temp_max;

    public Main(String temp, String pressure, String humidity, String temp_min, String temp_max) {
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
    }

    public String getTemp() {
        return temp;
    }

    public String getPressure() {
        return pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getTemp_min() {
        return temp_min;
    }

    public String getTemp_max() {
        return temp_max;
    }
}
