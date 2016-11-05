package com.example.juda.paging;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyPageAdapter adapter = new MyPageAdapter();
        ViewPager viewPager = (ViewPager)findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);

        //Starts with the 2nd page
        viewPager.setCurrentItem(1);
    }

    public void onClick (View view){
        int buttonTag = Integer.valueOf(view.getTag().toString());//which page clicked
        Toast.makeText(this, "Button " + Integer.toString(buttonTag)+ " clicked", Toast.LENGTH_SHORT).show();
    }
}
