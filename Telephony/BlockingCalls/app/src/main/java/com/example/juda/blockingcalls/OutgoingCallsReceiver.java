package com.example.juda.blockingcalls;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Juda on 22/11/2016.
 */

public class OutgoingCallsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String outGoingNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
        if (outGoingNumber.contains("0544556677")){
            setResultData(null);//block the outgoing call
            Toast.makeText(context, "This call is not allowed", Toast.LENGTH_SHORT).show();
        }
    }
}
