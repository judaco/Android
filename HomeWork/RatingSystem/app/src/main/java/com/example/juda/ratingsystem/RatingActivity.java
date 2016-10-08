package com.example.juda.ratingsystem;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.LinearLayout;

public class RatingActivity extends Activity{

    private LinearLayout rating;
    private CheckBox star;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rating);

        rating = (LinearLayout)findViewById(R.id.rating);//the pointer for the LinearLayout, the container of the stars

        for (int i = 1; i <= 5; i++) {
            star = (CheckBox)rating.findViewWithTag(String.valueOf(i));
            star.setOnClickListener(startsListener);
        }
    }

    private OnClickListener startsListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            int tag = Integer.valueOf((String)v.getTag());//the number of the tag of the star chosen

            for (int i = 1; i <= tag ; i++) {//check all the stars up to the one chosen
                star = (CheckBox)rating.findViewWithTag(String.valueOf(i));
                star.setChecked(true);
            }
            for (int i = tag + 1; i <= 5; i++) {//disappear all the remaining stars
                star = (CheckBox)rating.findViewWithTag(String.valueOf(i));
                star.setChecked(false);
            }
        }
    };
}
