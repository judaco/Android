package com.example.juda.passbetweenactivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Juda on 24/09/2016.
 */

public class SecondActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    public void btnClick(View view) {

        Intent intent1 = new Intent(this, FirstActivity.class);
        Intent intent3 = new Intent(this, ThirdActivity.class);

        startActivity(Intent.createChooser(intent3, "Choose"));
    }
}
