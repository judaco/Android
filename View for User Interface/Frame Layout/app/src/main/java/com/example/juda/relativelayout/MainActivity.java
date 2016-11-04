package com.example.juda.relativelayout;

import android.os.StrictMode;
import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    int count = 1;
    FrameLayout frameLayout;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frameLayout = (FrameLayout)findViewById(R.id.framelayout);
    }

    public void btnClick(View view) {
        imageView = (ImageView)frameLayout.findViewWithTag(String.valueOf(count));
        imageView.setVisibility(View.INVISIBLE);
        count++;
        if (count > 3)
            count = 1;
        imageView = (ImageView)frameLayout.findViewWithTag(String.valueOf(count));
        imageView.setVisibility(View.VISIBLE);
    }

    public void btnPrevious(View view) {
        imageView = (ImageView)frameLayout.findViewWithTag(String.valueOf(count));
        imageView.setVisibility(View.INVISIBLE);
        count--;
        if (count < 1)
            count = 3;
        imageView = (ImageView)frameLayout.findViewWithTag(String.valueOf(count));
        imageView.setVisibility(View.VISIBLE);
    }
}
