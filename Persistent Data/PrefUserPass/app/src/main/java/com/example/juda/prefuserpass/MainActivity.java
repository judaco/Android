package com.example.juda.prefuserpass;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class MainActivity extends Activity {

    EditText username,password;
    Button button;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        button = (Button)findViewById(R.id.button);

        preferences = getSharedPreferences("login",MODE_PRIVATE);

        //if SharedPreferences contains username and password then redirect to Home activity
        if (preferences.contains("username") && preferences.contains("password")){
            startActivity (new Intent(MainActivity.this, LogoutActivity.class));
            finish();   //finish current activity
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginCheck();
            }
        });
    }

    void loginCheck(){
        //check username and password are correct and then add them to SharedPreferences
        if(username.getText().toString().equals("Juda") && password.getText().toString().equals("1234")){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("username","Juda");
            editor.putString("password","1234");
            editor.commit();

            Toast.makeText(MainActivity.this,"Login Successful",Toast.LENGTH_LONG).show();

            startActivity(new Intent(MainActivity.this,LogoutActivity.class));
            finish();
        }
        else{
            Toast.makeText(MainActivity.this,"Incorrect Login Details",Toast.LENGTH_LONG).show();
        }
    }
}
