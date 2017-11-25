package com.example.juda.asyncthreads.counter_task;

/**
 * Created by Juda on 25/11/2017.
 */

public interface IAsyncTaskEvents {

    void onPreExecute();

    void onPostExecute();

    void onProgressUpdate(Integer integer);

    void onCancel();
}
