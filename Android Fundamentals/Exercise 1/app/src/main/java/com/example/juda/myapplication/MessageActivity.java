package com.example.juda.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MessageActivity extends AppCompatActivity {

    private static final String MSG = "My message";
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        //Getting the text from the EditText in my new Activity
        textView = (TextView) findViewById(R.id.tv);
        String msg = getIntent().getStringExtra(MSG);
        textView.setText(msg);
    }
}
