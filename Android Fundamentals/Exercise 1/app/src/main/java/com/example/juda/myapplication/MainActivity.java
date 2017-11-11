package com.example.juda.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText message;
    private Button btnActivity;
    private Button btnEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        message = (EditText) findViewById(R.id.message);
        btnActivity = (Button) findViewById(R.id.activity_button);
        btnEmail = (Button) findViewById(R.id.email_button);

    }
    //Explicit Intent
    public void openActivity(View view) {
        Intent intent = new Intent(MainActivity.this, MessageActivity.class);
        intent.putExtra("My message", message.getText().toString());
        startActivity(intent);
    }
    //Implicit Intent
    public void openEmail(View view) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Hello it's me");
        intent.putExtra(Intent.EXTRA_TEXT, message.getText().toString());

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
