package com.example.juda.makingcalls;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

/**
 * Created by Juda on 22/11/2016.
 */

public class PhoneStateReceiver extends BroadcastReceiver {

    TelephonyManager telephonyManager;
    PhoneStateListener listener;
    boolean alreadyListening;//if we are on listening or not

    @Override
    public void onReceive(Context context, Intent intent) {
        listener = new PhoneReceiver(context);
        telephonyManager = (TelephonyManager)context.getSystemService(
                Context.TELEPHONY_SERVICE);
        //Do not add the listener more than once
        if(!alreadyListening){
            telephonyManager.listen(listener,
                    PhoneStateListener.LISTEN_CALL_STATE);
            alreadyListening = true;
        }
    }
}
