package com.example.juda.weathy.json;

import java.util.List;

/**
 * Created by Juda on 06/08/2017.
 */

public class Forecast {

    private List<FiveWeathers> list;

    public Forecast(List<FiveWeathers> list) {
        this.list = list;
    }

    public List<FiveWeathers> getList() {
        return list;
    }
}
