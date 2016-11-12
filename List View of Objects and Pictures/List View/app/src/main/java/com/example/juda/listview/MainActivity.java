package com.example.juda.listview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    ListView list1;
    String[] clubNames = {
      "Liverpool FC", "Manchester City FC", "Manchester United FC","FC Barcelona", "Arsenal FC",
            "Chelsea FC", "Real Madrid CF", "Sevilla FC", "Club Atletico de Madrid", "FC Porto",
            "SL Benfica", "Sporting CP", "AC Milan", "AS Roma", "Juventus FC", "SSC Napoli",
            "AFC Ajax", "Feyenoord", "PSV Eindhoven", "Paris Saint Germain FC", "AS Monaco FC",
            "Olympique de Marseille", "Olympique Lyonnais", "FC Bayern Munchen", "Bayer 04 Leverkusen",
            "BV Borussia Dortmund",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list1 = (ListView)findViewById(R.id.list1);

        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, clubNames);*/
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.item_club, R.id.text1, clubNames);
        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_checked, clubNames);*/
        List<Club> clubs = new ArrayList<>();
        for (String clubName : clubNames){
            Club club = new Club(clubName,0);
            switch (clubName){
                case "SL Benfica":
                    club.setImage(R.drawable.sl_benfica);
                    break;
                case "AFC Ajax":
                    club.setImage(R.drawable.afc_ajax);
                    break;
                case "Real Madrid FC":
                    club.setImage(R.drawable.real_madrid_cf);
                    break;
                case "FC Barcolna":
                    club.setImage(R.drawable.fc_barcelona);
                    break;
                case "FC Bayern Munchen":
                    club.setImage(R.drawable.fc_bayern_munchen);
                    break;
                case "Liverpool FC":
                    club.setImage(R.drawable.liverpool_fc);
                    break;
                case "FC Porto":
                    club.setImage(R.drawable.fc_porto);
                    break;
                case "Paris Saint Germain FC":
                    club.setImage(R.drawable.paris_saint_germain_fc);
                    break;
                case "Olympique Lyonnais":
                    club.setImage(R.drawable.olympique_de_marseille);
                    break;
                case "AC Milan":
                    club.setImage(R.drawable.ac_milan);
                    break;
                case "Manchester City FC":
                    club.setImage(R.drawable.manchester_city_fc);
                    break;
                case "Sevilla FC":
                    club.setImage(R.drawable.sevilla_fc);
                    break;
                case "AS Monaco FC":
                    club.setImage(R.drawable.as_monaco_fc);
                    break;
                case "AS Roma":
                    club.setImage(R.drawable.as_roma);
                    break;
                case "Arsenal FC":
                    club.setImage(R.drawable.arsenal_fc);
                    break;
                case "BV Borussia Dortmund":
                    club.setImage(R.drawable.bv_borussia_dortmund);
                    break;
                case "Bayer 04 Leverkusen":
                    club.setImage(R.drawable.bayer_04_leverkusen);
                    break;
                case "Chelsea FC":
                    club.setImage(R.drawable.chelsea_fc);
                    break;
                case "Club Atletico de Mardid":
                    club.setImage(R.drawable.club_atletico_de_madrid);
                    break;
                case "Feyenoord":
                    club.setImage(R.drawable.feyenoord);
                    break;
                case "Juventus FC":
                    club.setImage(R.drawable.juventus_fc);
                    break;
                case "Manchester United FC":
                    club.setImage(R.drawable.manchester_united_fc);
                    break;
                case "Olympique de Marseille":
                    club.setImage(R.drawable.olympique_de_marseille);
                    break;
                case "PSV Eindhoven":
                    club.setImage(R.drawable.psv_eindhoven);
                    break;
                case "SSC Napoli":
                    club.setImage(R.drawable.ssc_napoli);
                    break;
                case "Sporting CP":
                    club.setImage(R.drawable.sporting_cp);
                    break;
            }
            clubs.add(club);
        }

        list1.setAdapter(adapter);
        //list1.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //CheckedTextView checkedTextView = (CheckedTextView)view;
                //boolean checked = checkedTextView.isChecked();
                Toast.makeText(MainActivity.this, "You clicked on " + clubNames[position], Toast.LENGTH_SHORT).show();
            }
        });
    }
}
