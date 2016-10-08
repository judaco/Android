package com.example.juda.timerpicker;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;

public class MainActivity extends AppCompatActivity {

    TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timePicker = (TimePicker)findViewById(R.id.timePicker);

        timePicker.setIs24HourView(true);
    }

    public void btnOnClick (View view){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            int minute = timePicker.getMinute();
        }//else{
           // timePicker.getCurrentMinute();
        }
    }
