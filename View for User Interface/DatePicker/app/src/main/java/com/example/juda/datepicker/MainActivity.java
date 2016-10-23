package com.example.juda.datepicker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        datePicker = (DatePicker)findViewById(R.id.datePicker);
    }

    public void btnOnClick (View view){
        Toast.makeText(this, "date" + datePicker.getDayOfMonth() + "/"
                + (datePicker.getMonth()+1) + "/" +
                datePicker.getYear(), Toast.LENGTH_SHORT).show();
    }
}
