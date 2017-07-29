package com.example.juda.weathy;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * Created by Juda on 14/07/2017.
 */

public class WeathyIntentService extends IntentService {

    public WeathyIntentService() {
        super("WeathyIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        WeathySyncTask.syncWeather(this);
    }
}
