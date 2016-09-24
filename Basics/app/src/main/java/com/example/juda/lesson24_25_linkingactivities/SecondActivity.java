package com.example.juda.lesson24_25_linkingactivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Juda on 23/09/2016.
 */

public class SecondActivity extends Activity {

    public static final String PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intent = getIntent();//extends from Main Activity

      /*  String firstName = intent.getStringExtra(MainActivity.FIRST_NAME);
        int age = intent.getIntExtra(MainActivity.AGE, -1);//value must be positive cannot be minus
        */
        //other option with Bundle
        Bundle extras = intent.getExtras();
        String firstName = extras.getString(MainActivity.FIRST_NAME);
        int age = extras.getInt(MainActivity.AGE, -1);

        Toast.makeText(this, firstName, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "lastName", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, String.valueOf(age), Toast.LENGTH_SHORT).show();

        Person p = (Person) extras.getSerializable("person");
        Toast.makeText(this, "person: " + p.getFirstName(), Toast.LENGTH_SHORT).show();
    }
    public void btnGoBack(View view){
        Intent data = new Intent();//other role for the intent - put default constructor
        data.putExtra(PASSWORD, "2010");

        setResult(RESULT_OK, data);//the client accomplished the action successfully
        finish();
    }
}
