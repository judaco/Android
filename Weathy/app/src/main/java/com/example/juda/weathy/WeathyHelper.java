package com.example.juda.weathy;

import android.app.Application;
import android.content.res.AssetManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Juda on 23/07/2017.
 */

public class WeathyHelper extends Application {

    public InputStream getJsonInputStream(){
        AssetManager assetManager = getAssets();
        String fileName = null;
        InputStream inputStream = null;
        try {
            fileName = "city.list.json";
            System.out.println("file nmae : " + fileName);
            inputStream = assetManager.open(fileName, AssetManager.ACCESS_BUFFER);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    public List<JsonObjectList> readStream (InputStream inputStream) {
        JsonReader jsonReader = null;
        List<JsonObjectList> messages = new ArrayList<JsonObjectList>();
        try {
            jsonReader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
            Gson gson = new GsonBuilder().create();
            jsonReader.beginArray();
            while (jsonReader.hasNext()) {
                JsonObjectList message = gson.fromJson(jsonReader, JsonObjectList.class);
                messages.add(message);
            }
            jsonReader.endArray();
            jsonReader.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.sort(messages, new Comparator<JsonObjectList>() {
            @Override
            public int compare(JsonObjectList jsonObjectList, JsonObjectList t1) {
                return jsonObjectList.getName().compareCase(t1.getName());
            }
        });
        return messages;
    }
}
