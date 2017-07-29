package com.example.juda.weathy;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.ThemedSpinnerAdapter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Juda on 28/07/2017.
 */

public class WeathySharedPref {

    private SharedPreferences sharedPreferences;
    private Gson gson;

    public WeathySharedPref (Context context) {
        sharedPreferences = context.getSharedPreferences(Assistant.PREFERENCE_TAG,
                Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public void setDataFromSP(String key, List<JsonObjectList> listObjects) {
        String json = gson.toJson(listObjects);
        sharedPreferences.edit().putString(key, json).apply();;
    }

    public List<JsonObjectList> getAllDataObject (String key) {
        String objects = sharedPreferences.getString(key, "");
        Type type = new TypeToken<JsonObjectList>(){}.getType();
        return gson.fromJson(objects, type);
    }

    public void setDataIfPresent (boolean isData) {
        sharedPreferences.edit().putBoolean(Assistant.IS_DATA_PRESENT, isData).apply();
    }

    public boolean getDataIfPresent() {
        return sharedPreferences.getBoolean(Assistant.IS_DATA_PRESENT, false);
    }

    public void setLocationInPref (String cityName) {
        sharedPreferences.edit().putString(Assistant.LOCATION_PREFERENCE, cityName).apply();
    }

    public String getLocationInPref() {
        return sharedPreferences.getString(Assistant.LOCATION_PREFERENCE, "");
    }
}
