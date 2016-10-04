package com.example.juda.buttons;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Button 3 was clicked", Toast.LENGTH_SHORT).show();
            }
        });

        Button button4 = (Button) findViewById(R.id.button4);
        button4.setOnClickListener(btnListener);//pointer to the same object
        Button button5 = (Button) findViewById(R.id.button5);
        button5.setOnClickListener(btnListener);
    }

    private View.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(MainActivity.this, ((Button)v).getText() + " was clicked!", Toast.LENGTH_SHORT).show();
        }
    };

    public void btnOnToggle(View view) {
        ToggleButton toggleButton = (ToggleButton)view;
        Toast.makeText(MainActivity.this, (toggleButton.isChecked() ? "ON" : "OFF"), Toast.LENGTH_SHORT).show();
    }

    public void btnOnClick(View view) {
        Toast.makeText(MainActivity.this, ((Button)view).getText() + " was clicked", Toast.LENGTH_SHORT).show();
    }
}
