package com.example.juda.asyncthreads.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.juda.asyncthreads.R;

public class MainActivity extends AppCompatActivity {

    private Button asyncTask, threads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        asyncTask = findViewById(R.id.asnyctask);
        threads = findViewById(R.id.threads);
    }

    public void btnAsync(View view) {
        Intent intent = new Intent(MainActivity.this, AsyncTaskActivity.class);
        startActivity(intent);
    }

    public void btnThread(View view) {
        Intent intent = new Intent(MainActivity.this, ThreadsActivity.class);
        startActivity(intent);
    }
}
