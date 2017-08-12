package com.example.juda.weathy.json;

/**
 * Created by Juda on 06/08/2017.
 */

public class WeatherResults {

    private String id;
    private String main;
    private String description;
    private String icon;

    public WeatherResults(String id, String main, String description, String icon) {
        this.id = id;
        this.main = main;
        this.description = description;
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public String getMain() {
        return main;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }
}
