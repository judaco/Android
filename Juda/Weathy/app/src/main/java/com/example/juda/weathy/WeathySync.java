package com.example.juda.weathy;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.view.View;

import com.example.juda.weathy.data.WeathyContract;
import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

/**
 * Created by Juda on 14/07/2017.
 */

public class WeathySync {

    private static boolean hasInit;

    private static final int SYNC_INTERVAL_HOURS = 1;
    private static final int SYNC_INTERVAL_SECONDS = 3600;
    private static final int SYNC_FLEXTIME_SECONDS = SYNC_INTERVAL_SECONDS / 3;

    static void schedueleSync (@NonNull final Context context) {
        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher jobDispatcher = new FirebaseJobDispatcher(driver);

        Job syncJob = jobDispatcher.newJobBuilder().setService(FirebaseJobService.class)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(
                        SYNC_INTERVAL_SECONDS,
                        SYNC_FLEXTIME_SECONDS + SYNC_FLEXTIME_SECONDS
                ))
                .setReplaceCurrent(true).build();
        jobDispatcher.schedule(syncJob);
    }

    synchronized public static void init (@NonNull final Context context) {
        if (hasInit) {
            return;
        }
        hasInit = true;

        schedueleSync(context);

        Thread isEmpty = new Thread(new Runnable() {
            @Override
            public void run() {
                Uri queryUri = WeathyContract.WeathyEntry.CONTENT_URI;

                String[] projection = {WeathyContract.WeathyEntry._ID};
                String selection = WeathyContract.WeathyEntry.getSqlSelectorForToday();

                Cursor cursor = context.getContentResolver().query(
                        queryUri, projection, selection, null, null
                );
                if (cursor == null || cursor.getCount() == 0) {
                    startSyncImmediate(context);
                }
                cursor.close();
            }
        });
        isEmpty.start();
    }

    public static void startSyncImmediate(@NonNull final Context context) {
        Intent syncIntent = new Intent(context, WeathyIntentService.class);
        context.startService(syncIntent);
    }
}
