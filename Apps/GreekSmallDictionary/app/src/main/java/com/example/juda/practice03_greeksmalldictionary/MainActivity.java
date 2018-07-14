package com.example.juda.practice03_greeksmalldictionary;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);

        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        // Create an adapter that knows which fragment should be shown on each page
        CategoryAdapter adapter = new CategoryAdapter(this ,getSupportFragmentManager());

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
}

//        //Find the view that shows the categories
//        TextView numbers = (TextView) findViewById(R.id.numbers);
//        TextView family = (TextView) findViewById(R.id.family);
//        TextView colors = (TextView) findViewById(R.id.colors);
//        TextView phrases = (TextView) findViewById(R.id.phrases);
//        //Set the clickListener on that view
//        numbers.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent iNumbers = new Intent(MainActivity.this, Numbers.class);
//                startActivity(iNumbers);
//            }
//        });
//        family.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent iFamily = new Intent(MainActivity.this, Family.class);
//                startActivity(iFamily);
//            }
//        });
//        colors.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent iColors = new Intent(MainActivity.this, Colors.class);
//                startActivity(iColors);
//            }
//        });
//        phrases.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent iPhrases = new Intent(MainActivity.this, Phrases.class);
//                startActivity(iPhrases);
//            }
//        });


