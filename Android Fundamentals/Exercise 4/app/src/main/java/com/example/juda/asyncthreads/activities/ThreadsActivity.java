package com.example.juda.asyncthreads.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juda.asyncthreads.R;
import com.example.juda.asyncthreads.counter_task.CounterAsyncTask;
import com.example.juda.asyncthreads.counter_task.IAsyncTaskEvents;
import com.example.juda.asyncthreads.simple_task.MySimpleAsyncTask;

public class ThreadsActivity extends AppCompatActivity implements IAsyncTaskEvents{

    private static final String TAG = "ThreadsActivity";

    private Button mCreate, mStart, mCancel;
    private TextView mCounter;

    private MySimpleAsyncTask mCounterAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threads);
        setContentView(R.layout.activity_async_task);

        mCreate = findViewById(R.id.create);
        mStart = findViewById(R.id.start);
        mCancel = findViewById(R.id.cancel);
        mCounter = findViewById(R.id.counter_tv);
    }

    public void btnCreate(View view) {
        createAsyncTask();
    }

    public void btnStart(View view) {
        startAsyncTask();
    }

    public void btnCancel(View view) {
        cancelAsyncTask();
    }

    private void createAsyncTask() {
        Toast.makeText(this, getString(R.string.msg_created), Toast.LENGTH_SHORT).show();
        mCounterAsyncTask = new MyCounterAsyncTaskImpl(this);
    }

    private void startAsyncTask() {
        if ((mCounterAsyncTask == null) || (mCounterAsyncTask.isCancelled())) {
            Toast.makeText(this, getString(R.string.msg_should_created), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.msg_started, Toast.LENGTH_SHORT).show();
            mCounterAsyncTask.execute();
        }
    }

    private void cancelAsyncTask() {
        mCounterAsyncTask.cancel();
    }

    @Override
    public void onPreExecute() {
        Toast.makeText(this, R.string.msg_pre_execute, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPostExecute() {
        Toast.makeText(this, R.string.msg_post_execute, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProgressUpdate(Integer integer) {
        mCounter.setText(String.valueOf(integer));
    }

    @Override
    public void onCancel() {
        Toast.makeText(this, R.string.msg_oncancel, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        if (mCounterAsyncTask != null) {
            mCounterAsyncTask.cancel();
            mCounterAsyncTask = null;
        }
        super.onDestroy();
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, ThreadsActivity.class));
    }
}

