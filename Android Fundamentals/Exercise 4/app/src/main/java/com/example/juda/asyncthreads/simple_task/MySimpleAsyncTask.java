package com.example.juda.asyncthreads.simple_task;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by Juda on 25/11/2017.
 */

public abstract class MySimpleAsyncTask<Param> extends SimpleAsyncTask<Param> {

    private Thread backThread;

    protected abstract void onPreExecute();

    protected abstract Param doInBackground();

    protected abstract void onPostExecute();

    @Override
    public void execute() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                onPreExecute();
                backThread = new Thread("Handler_executor_thread") {
                    @Override
                    public void run() {
                        doInBackground();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                onPostExecute();
                            }
                        });
                    }
                };
                backThread.start();
            }
        });
    }

    private void runOnUiThread(Runnable runnable) {
        new Handler(Looper.getMainLooper()).post(runnable);
    }

    @Override
    public void cancel() {
        mCancelled = true;
        if (backThread != null) {
            backThread.interrupt();
        }
    }

    @Override
    protected void publishProgress(final Param... values) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                onProgressUpdate(values);
            }
        });
    }
}
