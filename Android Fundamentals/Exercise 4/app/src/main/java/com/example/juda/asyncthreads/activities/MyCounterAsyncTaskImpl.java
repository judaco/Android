package com.example.juda.asyncthreads.activities;

import android.os.SystemClock;

import com.example.juda.asyncthreads.counter_task.IAsyncTaskEvents;
import com.example.juda.asyncthreads.simple_task.MySimpleAsyncTask;

/**
 * Created by Juda on 25/11/2017.
 */

public class MyCounterAsyncTaskImpl extends MySimpleAsyncTask<Integer> {

    private IAsyncTaskEvents iAsyncTaskEvents;

    public MyCounterAsyncTaskImpl(IAsyncTaskEvents iAsyncTaskEvents) {
        this.iAsyncTaskEvents = iAsyncTaskEvents;
    }

    @Override
    protected void onPreExecute() {
        if (iAsyncTaskEvents != null) {
            iAsyncTaskEvents.onPreExecute();
        }
    }

    @Override
    protected Integer doInBackground() {

        int end = 10;
        for (int i = 0; i <= end ; i++) {
            if (isCancelled()) {
                return i;
            }
            publishProgress(i);
            SystemClock.sleep(500);
        }
        return end;
    }

    @Override
    protected void onPostExecute() {
        if (iAsyncTaskEvents != null) {
            iAsyncTaskEvents.onPostExecute();
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        if (iAsyncTaskEvents != null) {
            iAsyncTaskEvents.onProgressUpdate(values[0]);
        }
    }
}
