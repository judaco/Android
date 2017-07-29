package com.example.juda.weathy;

import android.app.PendingIntent;
import android.content.Context;


import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;

import com.example.juda.weathy.data.WeathyContract;
import com.example.juda.weathy.data.WeathyPref;

import static android.os.Build.VERSION_CODES.N;

/**
 * Created by Juda on 15/07/2017.
 */

public class Notifications {

    public static final String[] NOTIFICATION_PROJECTION = {
            WeathyContract.WeathyEntry.COLUMN_WEATHY_ID,
            WeathyContract.WeathyEntry.COLUMN_TEMP_MAX,
            WeathyContract.WeathyEntry.COLUMN_TEMP_MIN
    };

    public static final int INDEX_WEATHER_ID = 0;
    public static final int INDEX_TEMP_MAX = 1;
    public static final int INDEX_TEMP_MIN = 2;

    private static final int NOTIFICATION_ID = 3004;

    public static void notifyWeatherChanged(Context context) {
        Uri uriWeathToday = WeathyContract.WeathyEntry.weatherUriWithDateBuilder(
                DateConvert.normalizeDate(System.currentTimeMillis()));
        Cursor cursorWeathToday = context.getContentResolver().query(
                uriWeathToday, NOTIFICATION_PROJECTION, null, null, null
        );
        if (cursorWeathToday.moveToFirst()) {
            int weatherId = cursorWeathToday.getInt(INDEX_WEATHER_ID);
            double tempHigh = cursorWeathToday.getDouble(INDEX_TEMP_MAX);
            double tempLow = cursorWeathToday.getDouble(INDEX_TEMP_MIN);

            Resources resources = context.getResources();
            int bigPhotoResourceId = WeatherConvert.getLargeArtResourceIdForWeatherCondition(weatherId);

            Bitmap largeIcon = BitmapFactory.decodeResource(resources, bigPhotoResourceId);

            String notifTitle = context.getString(R.string.app_name);
            String notifText = getNotificationText(context, weatherId, tempHigh, tempLow);
            int smallPhotoResourceId = WeatherConvert.getSmallArtResourceIdForWeatherCondition(weatherId);

            NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(context)
                    .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    .setSmallIcon(smallPhotoResourceId)
                    .setLargeIcon(largeIcon)
                    .setContentTitle(notifTitle)
                    .setContentText(notifText)
                    .setAutoCancel(true);

            Intent intent = new Intent(context, WeathySelector.class);
            intent.setData(uriWeathToday);

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addNextIntentWithParentStack(intent);
            PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

            notifBuilder.setContentIntent(pendingIntent);

            NotificationManager notificationManager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(NOTIFICATION_ID, notifBuilder.build());
            WeathyPref.saveLastNotif(context, System.currentTimeMillis());
          }
        cursorWeathToday.close();
    }

        private static String getNotificationText (Context context,int weatherId,
        double high, double low){

         /*
          * Short description of the weather, as provided by the API.
          * e.g "clear" vs "sky is clear".
          */
            String shortDescription = WeatherConvert
                    .getStringForWeatherCondition(context, weatherId);

            String notificationFormat = context.getString(R.string.notification_format);

         /* Using String's format method, we create the forecast summary */
            String notificationText = String.format(notificationFormat,
                    shortDescription,
                    WeatherConvert.formatTemp(context, high),
                    WeatherConvert.formatTemp(context, low));

            return notificationText;
        }
    }

