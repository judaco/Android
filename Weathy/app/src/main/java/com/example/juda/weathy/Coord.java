package com.example.juda.weathy;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Juda on 05/08/2017.
 */

public class Coord {

    @SerializedName("lat")
    private String lat;

    @SerializedName("lon")
    private String lon;

    public Coord(String lat, String lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }
}
