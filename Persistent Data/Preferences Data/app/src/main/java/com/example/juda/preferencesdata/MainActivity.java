package com.example.juda.preferencesdata;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

    public static final String MY_PREF = "MyPref";
    public static final String MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences(MY_PREF, MODE_PRIVATE);

        String message = sharedPreferences.getString(MESSAGE, null);
        if (message != null){
            TextView lblMessage = (TextView)findViewById(R.id.lblMessage);
        }
        //how to write to shared preferences
       /* SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", "juda");
        editor.putInt("age", 27);
        editor.putString("nickname", "jude");
        editor.commit();

        //how to read from shred preferences

        String userName = sharedPreferences.getString("username", null);
        int age = sharedPreferences.getInt("age", -1);
        String nickName = sharedPreferences.getString("nickname", null);*/
    }

    public void btnSave(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences(MY_PREF, MODE_PRIVATE);
        EditText txtMessage = (EditText)findViewById(R.id.txtMessage);
        String message = txtMessage.getText().toString();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MESSAGE, message);
        editor.commit();
        TextView lblMessage = (TextView)findViewById(R.id.lblMessage);
        lblMessage.setText(message);
    }
}
