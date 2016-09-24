package com.example.juda.broadcastreceiver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent("My_Specific_Option");
        intent.putExtra("Juda", "Cossa");
        intent.putExtra("abita", "in");
        intent.putExtra("paese", "Israele");

        sendBroadcast(intent);
    }
}
