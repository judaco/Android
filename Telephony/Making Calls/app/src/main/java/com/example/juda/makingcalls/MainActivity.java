package com.example.juda.makingcalls;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class MainActivity extends AppCompatActivity {

    TelephonyManager telephonyManager;
    PhoneStateListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*String phoneNumber = "0544556677";
        //Dial
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
        //other option
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
        startActivity(intent);*/

        listener = new PhoneReceiver(this);
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //telephonyManager.listen(listener,
        //        PhoneStateListener.LISTEN_CALL_STATE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //telephonyManager.listen(listener,
        //        PhoneStateListener.LISTEN_NONE);
    }
}
