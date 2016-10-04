package com.example.juda.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Juda on 24/09/2016.
 */

public class MyBroadcastReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        String juda = intent.getStringExtra("Juda");
        Toast.makeText(context, "Juda " + juda, Toast.LENGTH_SHORT).show();
        abortBroadcast();//stop the string (intent), don't call to other broadcasts
    }
}
