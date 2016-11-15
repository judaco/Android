package com.example.juda.spinner;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Spinner s1;

        final String[] clubs = {
                "Liverpool FC", "Manchester City FC", "Manchester United FC", "FC Barcelona", "Arsenal FC",
                "Chelsea FC", "Real Madrid CF", "Sevilla FC", "Club Atletico de Madrid", "FC Porto",
                "SL Benfica", "Sporting CP", "AC Milan", "AS Roma", "Juventus FC", "SSC Napoli",
                "AFC Ajax", "Feyenoord", "PSV Eindhoven", "Paris Saint Germain FC", "AS Monaco FC",
                "Olympique de Marseille", "Olympique Lyonnais", "FC Bayern Munchen", "Bayer 04 Leverkusen",
                "BV Borussia Dortmund", "Maccabi Haifa FC"
        };

        s1 = (Spinner) findViewById(R.id.spinner1);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, clubs);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        s1.setAdapter(adapter);

        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // An item was selected. You can retrieve the selected item using
                // parent.getItemAtPosition(pos)
                int i = s1.getSelectedItemPosition();
                Toast.makeText(getBaseContext(), "You clicked on - " + clubs[i], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
    }

}
