package com.example.juda.weathy;

import android.content.Context;
import android.os.AsyncTask;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

/**
 * Created by Juda on 15/07/2017.
 */

public class FirebaseJobService extends JobService {

    private AsyncTask<Void, Void, Void> getWeatherTask;
    @Override
    public boolean onStartJob(final JobParameters job) {
        getWeatherTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                Context context = getApplicationContext();
                WeathySyncTask.syncWeather(context);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                jobFinished(job, false);
            }
        };
        getWeatherTask.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        if (getWeatherTask != null) {
            getWeatherTask.cancel(true);
        }
        return true;
    }
}
