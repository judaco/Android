package com.example.juda.radiobuttons;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String s = "";
                switch (checkedId){
                    case R.id.radio_button_1:
                        s = "button 1";
                        break;
                    case R.id.radio_button_2:
                        s = "button 2";
                        break;
                    case R.id.radio_button_3:
                        s = "button 3";
                        break;
                }
                Toast.makeText(MainActivity.this, s + " was clicked!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
