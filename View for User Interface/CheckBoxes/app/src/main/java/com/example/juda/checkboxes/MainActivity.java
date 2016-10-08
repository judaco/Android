package com.example.juda.checkboxes;

import android.content.ComponentCallbacks;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CheckBox chkAutoSave = (CheckBox) findViewById(R.id.chkAutosave);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.activity_main);
        CheckBox checkBox = (CheckBox) linearLayout.findViewWithTag("1");

        chkAutoSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if (v.getId() == R.id.chkAutosave){}
                //Integer.valueOf((String)v.getTag());
            }
        });

        chkAutoSave.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(MainActivity.this, "CheckBox is " +
                                (isChecked ? "checked" : "unchecked"),
                        Toast.LENGTH_SHORT).show();

            }
        });

        //chkAutosave.setOnCheckedChangeListener(new MyCheckedChangeListener(chkAutosave));
    }

    static class  MyCheckedChangeListener implements CompoundButton.OnCheckedChangeListener{

        private CheckBox chkAutoSave;

        public MyCheckedChangeListener(CheckBox chkAutoSave) {
            this.chkAutoSave = chkAutoSave;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            chkAutoSave.setText("NANA");
        }
    }
}
