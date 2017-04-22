package com.example.juda.practice04_earthquake;

import static com.example.juda.practice04_earthquake.R.id.date;

/**
 * Created by Juda on 21/04/2017.
 */

public class Earthquake {

    private String location;
    private double  magnitude;
    private long timeInMilliseconds;
    private String url;

    public Earthquake(double  magnitude, String location, long timeInMilliseconds, String url) {
        this.location = location;
        this.magnitude = magnitude;
        this.timeInMilliseconds = timeInMilliseconds;
        this.url = url;
    }

    public String getLocation() {
        return location;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public long getTimeInMilliseconds() {
        return timeInMilliseconds;
    }

    public String getUrl() {
        return url;
    }
}
