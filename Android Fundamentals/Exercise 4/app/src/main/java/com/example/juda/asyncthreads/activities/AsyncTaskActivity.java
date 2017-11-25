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

public class AsyncTaskActivity extends AppCompatActivity implements IAsyncTaskEvents {

    private static final String KEY_STRING = "";

    private Button mCreate, mStart, mCancel;
    private TextView mCounter;

    private CounterAsyncTask mCounterAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        mCounterAsyncTask = new CounterAsyncTask(this);
    }

    private void startAsyncTask() {
        if ((mCounterAsyncTask == null) || (mCounterAsyncTask.isCancelled())) {
            Toast.makeText(this, getString(R.string.msg_should_created), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.msg_started, Toast.LENGTH_SHORT).show();
            mCounterAsyncTask.execute(1, 10);
        }
    }

    private void cancelAsyncTask() {
        mCounterAsyncTask.cancel(true);
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
        mCounter.setText(R.string.done);
    }

    @Override
    public void onCancel() {
        Toast.makeText(this, R.string.msg_oncancel, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        if (mCounterAsyncTask != null) {
            mCounterAsyncTask.cancel(false);
            mCounterAsyncTask = null;
        }
        super.onDestroy();
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, AsyncTaskActivity.class));
    }
}
