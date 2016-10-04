package com.example.juda.broadcastreceiver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent("MY_SPECIFIC_ACTION");
        intent.putExtra("Juda", "Cossa");
        sendBroadcast(intent);//no priority, all random
        sendOrderedBroadcast(intent, null);//the priority is important
    }
}
