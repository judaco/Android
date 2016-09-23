package com.example.juda.lesson24_25_linkingactivities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ViewPropertyAnimatorCompatSet;
import android.view.View;

public class MainActivity extends Activity{

    public static final String FIRST_NAME = "firstName";
    public static final String AGE = "age";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnClick (View view){
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra(FIRST_NAME, "Juda");
        intent.putExtra(AGE, "29");
    }
}
