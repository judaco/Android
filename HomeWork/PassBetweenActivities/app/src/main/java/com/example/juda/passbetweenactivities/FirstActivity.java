package com.example.juda.passbetweenactivities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class FirstActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
    }

    public void btnClick(View view){
        Intent intent2 = new Intent(this, SecondActivity.class);
        Intent intent3 = new Intent(this, ThirdActivity.class);

        startActivity(Intent.createChooser(intent2, "Choose"));
    }
}
