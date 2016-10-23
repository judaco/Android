package com.example.juda.rankingsystem;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

    LinearLayout rating;
    CheckBox star;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rating = (LinearLayout)findViewById(R.id.rating);
        for (int i = 1; i <= 5; i++) {
            star = (CheckBox)rating.findViewWithTag(String.valueOf(i));
            star.setOnClickListener(starsClickListener);
        }

    }

    private View.OnClickListener starsClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int tag = Integer.valueOf((String)v.getTag());
            for (int i = 1; i <= 5; i++) {
                star = (CheckBox)rating.findViewWithTag(String.valueOf(i));
                star.setChecked(i <= tag);
            }
        }
    };
}
