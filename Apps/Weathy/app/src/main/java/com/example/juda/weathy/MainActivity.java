package com.example.juda.weathy;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.google.common.collect.Lists;
import com.google.common.math.IntMath;

import java.io.InputStream;
import java.math.RoundingMode;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private final int SPLASH_DISPLAY_LENGTH = 5000;
    private WeathySharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.hide();
        }

        sharedPref = new WeathySharedPref(MainActivity.this);
        if (!sharedPref.getDataIfPresent()) {
            PrepareDataSource dataSource = new PrepareDataSource();
            dataSource.execute();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, WeathyActivity.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
          }, SPLASH_DISPLAY_LENGTH);
    }

    private class PrepareDataSource extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onProgressUpdate(Void... values) {
        }

        @Override
        protected void onPostExecute(Void aVoid) {

        }

        @Override
        protected Void doInBackground(Void... voids) {
            InputStream inputStream = ((WeathyHelper)getApplication()).getJsonInputStream();
            List<JsonObjectList> dataSourceStore = ((WeathyHelper)getApplication()).readStream(inputStream);
            //Store the data in shared reference
            int size = IntMath.divide(dataSourceStore.size(), 2, RoundingMode.UP);
            List<List<JsonObjectList>> partitions = Lists.partition(dataSourceStore, size);
            List<JsonObjectList> firstListObject = partitions.get(0);
            List<JsonObjectList> secondListObject = partitions.get(1);

            sharedPref.setDataFromSP(Assistant.FIRST_DATA_STORED, firstListObject);
            sharedPref.setDataFromSP(Assistant.SECOND_DATA_STORED, secondListObject);
            sharedPref.setDataIfPresent(true);

            return null;
        }
    }
}
