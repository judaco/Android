package com.example.juda.autocomplete;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class MainActivity extends Activity {

    String [] cities = {"Tel Aviv", "Beer Sheva", "Jeruslaem", "Moskva", "Tbilisi","Kiev", "Minsk", "Baku",
    "Yerevan", "Almaty", "Chisinau", "Bucuresti", "Budapest", "Sofia", "Istanbul", "Ankara", "Nicosia",
    "Athens", "Rome", "Barcelona", "Madrid", "Milan", "Berlin", "Bratislava", "Prague", "Helsinki", "Tallinn",
    "Riga", "Vilnius", "Stockholm", "Torshavn", "Oslo", "Copenhagen", "Amsterdam", "Bruxelles", "London", "Glasgow",
    "Belfast", "Dublin", "Cardiff", "Porto", "Lisbon", "Andorra La Vella", "San Marino", "Vaduz", "Luxembourg",
    "Bern", "Zurich", "Tirana", "Belgrade", "Ljubljana", "Vienna", "Prishtina", "Skopje", "Zagreb", "Sarajevo",
    "Paris", "Marseille", "Lyon"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, cities);

        AutoCompleteTextView txtCity = (AutoCompleteTextView)findViewById(R.id.txtCity);
        txtCity.setThreshold(2);//after 2 letters the list suggest the cities
        txtCity.setAdapter(adapter);
    }
}
