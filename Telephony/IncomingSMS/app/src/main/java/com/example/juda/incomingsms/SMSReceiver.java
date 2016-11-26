package com.example.juda.incomingsms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.ObbInfo;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Juda on 26/11/2016.
 */

public class SMSReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //Get the SMS message passed in
        Bundle bundle = intent.getExtras();
        SmsMessage[] messages = null;
        String string = "SMS From";
        if (bundle != null){
        //Retrieve the SMS message received
            Object [] pdus = (Object[])bundle.get("pdus");
            messages = new  SmsMessage[pdus.length];
            for (int i = 0; i < pdus.length; i++) {
                messages[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                if (i == 0){
        //Get the sender address or phone number
                    string += messages[i].getOriginatingAddress();
                    string += ": ";
                }
        //Get the message body
                string += messages[i].getMessageBody();
            }
        //Dispaly the new SMS message
            Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
        }
        //Prevent this SMS message from being broadcasted outside
        Log.d("Juda", string);
        abortBroadcast();
    }
}
