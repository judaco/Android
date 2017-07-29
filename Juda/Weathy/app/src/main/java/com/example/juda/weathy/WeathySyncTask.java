package com.example.juda.weathy;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.text.format.DateUtils;

import com.example.juda.weathy.data.WeathyContract.WeathyEntry;
import com.example.juda.weathy.data.WeathyPref;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Juda on 14/07/2017.
 */

public class WeathySyncTask {

    synchronized public static void syncWeather(Context context) {
        try {
            URL requestUrl = NetworkConnection.getUrl(context);
            String jsonResponse = NetworkConnection.getResponseFromHTTPUrl(requestUrl);

            ContentValues[] values = WeathyJSON.getFullDataFromJson(context, jsonResponse);
            if (values != null && values.length != 0) {
                ContentResolver contentResolver = context.getContentResolver();
                contentResolver.delete(
                        WeathyEntry.CONTENT_URI, null, null
                );
                contentResolver.bulkInsert(WeathyEntry.CONTENT_URI, values);

                boolean enableNotification = WeathyPref.isNotificationEnabled(context);
                long timeSinceLastNotif = WeathyPref.getLastNotifSinceElapsed(context);
                boolean isOneDayPassed = false;

                if (timeSinceLastNotif >= DateUtils.DAY_IN_MILLIS) {
                    isOneDayPassed = true;
                }

                if (enableNotification && isOneDayPassed) {
                    Notifications.notifyWeatherChanged(context);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
