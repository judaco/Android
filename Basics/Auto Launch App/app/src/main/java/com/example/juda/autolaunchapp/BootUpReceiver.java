package com.example.juda.autolaunchapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Juda on 04/10/2016.
 */

public class BootUpReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "App_Started", Toast.LENGTH_SHORT).show();
        Intent intent1 = new Intent(context, MainActivity.class);//start the main activity of our app
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent1);
    }
}
