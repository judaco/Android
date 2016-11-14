package com.example.juda.twolistsview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ListView list1, list2;

    String[] clubs = {
            "Liverpool FC", "Manchester City FC", "Manchester United FC","FC Barcelona", "Arsenal FC",
            "Chelsea FC", "Real Madrid CF", "Sevilla FC", "Club Atletico de Madrid", "FC Porto",
            "SL Benfica", "Sporting CP", "AC Milan", "AS Roma", "Juventus FC", "SSC Napoli",
            "AFC Ajax", "Feyenoord", "PSV Eindhoven", "Paris Saint Germain FC", "AS Monaco FC",
            "Olympique de Marseille", "Olympique Lyonnais", "FC Bayern Munchen", "Bayer 04 Leverkusen",
            "BV Borussia Dortmund"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, clubs);

        //List View 1

        list1 = (ListView)findViewById(R.id.list1);
        list1.setAdapter(adapter);
        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int index = position;
                Toast.makeText(getBaseContext(), "You clicked on - " + clubs[index], Toast.LENGTH_SHORT).show();
            }
        });

        //List View 2
        list2 = (ListView)findViewById(R.id.list2);
        list2.setAdapter(adapter);
        list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int index = position;
                Toast.makeText(getBaseContext(), "You clicked on - " + clubs[index], Toast.LENGTH_SHORT).show();
            }
        });
    }
}
