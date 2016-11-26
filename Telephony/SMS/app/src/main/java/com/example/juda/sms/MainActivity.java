package com.example.juda.sms;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

    public static final String SMS_SENT = "sms sent";
    PendingIntent sendPI;
    BroadcastReceiver smsSentReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    //Send SMS via the built-in App
       /* Intent intent = new Intent(Intent.ACTION_VIEW);//the Dial
        intent.putExtra("address", "0544336677"; "0511225544"; "0588991155");
        intent.putExtra("sms_body", "Black Friday Sale");
        intent.setType("vnd.android-dir/mms-sms");
        startActivity(intent);*/

    //SMS sent and getting the result of it
        sendPI = PendingIntent.getBroadcast(this,0, new Intent(SMS_SENT), 0);
    }


    public void btnSend(View view) {
        smsSentReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String result = "";
                switch (getResultCode()){
                    case RESULT_OK:
                        result = "OK";
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        result = "Error";
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        result = "No Service";
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        result = "PDU Null";
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        result = "Radio Off";
                        break;

                }
                Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
            }
        };
        registerReceiver(smsSentReceiver, new IntentFilter(SMS_SENT));
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage("0500115566", null, "Where are you?", sendPI, null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(smsSentReceiver);
    }
}
