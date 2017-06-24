package com.example.juda.weathy;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Juda on 24/06/2017.
 */

public class NetworkConnection {

    private static final String format = "json";
    private static final String units = "metric";
    private static final int numDays = 10;

    final static String QUERY = "query";
    final static String LATITUDE = "lat";
    final static String LONGITUDE = "lon";
    final static String FORMAT = "mode";
    final static String UNITS = "units";
    final static String Days = "cnt";

    public static URL url(String locationQuery){
        return null;
    }

    public static URL url(Double latitude, Double longitude) {
        return null;
    }

    public static String getResponseFromHTTPUrl (URL url) throws IOException{
        HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
        try {
            InputStream inputStream = httpURLConnection.getInputStream();

            return null;
        }finally {
            httpURLConnection.disconnect();
        }
    }
}
