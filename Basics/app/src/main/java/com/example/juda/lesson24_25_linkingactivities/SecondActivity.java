package com.example.juda.lesson24_25_linkingactivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Juda on 23/09/2016.
 */

public class SecondActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intent = getIntent();//extends from Main Activity


        String firstName = intent.getStringExtra(MainActivity.FIRST_NAME);
        int age = intent.getIntExtra(MainActivity.AGE, -1);//value must be positive cannot be minus

        //other option with Bundle
    }
}
