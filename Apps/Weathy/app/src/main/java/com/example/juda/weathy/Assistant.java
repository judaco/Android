package com.example.juda.weathy;

/**
 * Created by Juda on 28/07/2017.
 */

public class Assistant {

    public static final String LOCATION_PREFERENCE = "location_preference";
    public static final String LOCATION_LIST = "Location List";
    public static final String LOCATION_MANAGER = "Location Manager";
    public static final String LOCATION_ERROR_MSG = "please fill the field";
    public static final String SERVER_PATH = "api.openweathermap.org/data/2.5/weather?";
    public static final String SERVER_QUERY = "q";
    public static final String PREFERENCE_TAG = "Preference";
    public static final String FIRST_DATA_STORED = "first_data";
    public static final String SECOND_DATA_STORED = "second_data";
    public static final String IS_DATA_PRESENT = "isData";

    public static String firstLetterCapitalization (String original) {
        if (original == null || original.length() == 0) {
            return original;
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }
}
