package com.example.juda.weathy.json;

/**
 * Created by Juda on 06/08/2017.
 */

public class Wind {

    private String speed;
    private String deg;

    public Wind(String speed, String deg) {
        this.speed = speed;
        this.deg = deg;
    }

    public String getSpeed() {
        return speed;
    }

    public String getDeg() {
        return deg;
    }
}
