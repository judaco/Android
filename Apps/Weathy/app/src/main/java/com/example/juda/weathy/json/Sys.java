package com.example.juda.weathy.json;

/**
 * Created by Juda on 06/08/2017.
 */

public class Sys {

    private String type;
    private String id;
    private String message;
    private String country;
    private String sunrise;
    private String sunset;

    public Sys(String type, String id, String message, String country, String sunrise, String sunset) {
        this.type = type;
        this.id = id;
        this.message = message;
        this.country = country;
        this.sunrise = sunrise;
        this.sunset = sunset;
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public String getCountry() {
        return country;
    }

    public String getSunrise() {
        return sunrise;
    }

    public String getSunset() {
        return sunset;
    }
}
