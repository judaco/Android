package com.example.juda.callingbuiltinapps;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        //Google Map
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("geo:32.084673, 34.8028977"));//parse = le'hamir
        startActivity(intent);
        */

        /*
        //Google Play
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=se.appfamily.superpuzzlefree"));
        startActivity(intent);
        */

        /*
        //Send email - Creating a mail for client, he need to click on send button
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:"));
        String [] to = {"some1@gmail.com", "some2@gmail.com"};
        String [] cc = {"some3@gmail.com", "some4@gmail.com"};
        intent.putExtra(Intent.EXTRA_EMAIL, to);
        intent.putExtra(intent.EXTRA_CC, cc);
        intent.putExtra(intent.EXTRA_SUBJECT, "Ciao Bella");
        intent.putExtra(intent.EXTRA_TEXT, "Come Stai?");
        intent.setType("message/rfc822");//teken of mails
        startActivity(intent);
        */

        Intent intent = new Intent("SEARCH_PEOPLE");
        intent.putExtra("firstName", "Sagi");
        intent.putExtra("lastName", "Ribak");
        startActivity(intent);
    }
}
