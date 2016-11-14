package com.example.juda.listview;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    String[] clubNames = {
      "Liverpool FC", "Manchester City FC", "Manchester United FC","FC Barcelona", "Arsenal FC",
            "Chelsea FC", "Real Madrid CF", "Sevilla FC", "Club Atletico de Madrid", "FC Porto",
            "SL Benfica", "Sporting CP", "AC Milan", "AS Roma", "Juventus FC", "SSC Napoli",
            "AFC Ajax", "Feyenoord", "PSV Eindhoven", "Paris Saint Germain FC", "AS Monaco FC",
            "Olympique de Marseille", "Olympique Lyonnais", "FC Bayern Munchen", "Bayer 04 Leverkusen",
            "BV Borussia Dortmund"
    };
    //1 more way to enter images into the list
    int [] images = {R.drawable.liverpool_fc,R.drawable.manchester_city_fc,R.drawable.manchester_united_fc,
    R.drawable.fc_barcelona,R.drawable.arsenal_fc,R.drawable.chelsea_fc,R.drawable.real_madrid_cf,
    R.drawable.sevilla_fc,R.drawable.club_atletico_de_madrid,R.drawable.fc_porto,R.drawable.sl_benfica,
    R.drawable.sporting_cp,R.drawable.ac_milan,R.drawable.as_roma,R.drawable.juventus_fc,R.drawable.ssc_napoli,
    R.drawable.afc_ajax,R.drawable.feyenoord,R.drawable.psv_eindhoven,R.drawable.paris_saint_germain_fc,
    R.drawable.as_monaco_fc,R.drawable.olympique_de_marseille,R.drawable.olympique_lyonnais,R.drawable.fc_bayern_munchen,
    R.drawable.bayer_04_leverkusen,R.drawable.bv_borussia_dortmund};

    ListView list1;
    List<Club>clubs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list1 = (ListView)findViewById(R.id.list1);
        clubs = new ArrayList<>();
        for (int i = 0; i < clubNames.length ; i++) {
            Club c = new Club(clubNames[i], i%3 == 0, R.mipmap.ic_launcher);

        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, clubNames);*/
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
          //    R.layout.item_club, R.id.txtClub, clubNames);
        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_checked, clubNames);*/
        //final List<Club> clubs = new ArrayList<>();
        /*for (String clubName : clubNames){
            Club c = new Club(clubNames,0);
           switch (clubNames){
                case "SL Benfica":
                    c.setImage(R.drawable.sl_benfica);
                    break;
                case "AFC Ajax":
                    c.setImage(R.drawable.afc_ajax);
                    break;
                case "Real Madrid FC":
                    c.setImage(R.drawable.real_madrid_cf);
                    break;
                case "FC Barcolna":
                    c.setImage(R.drawable.fc_barcelona);
                    break;
                case "FC Bayern Munchen":
                    c.setImage(R.drawable.fc_bayern_munchen);
                    break;
                case "Liverpool FC":
                    c.setImage(R.drawable.liverpool_fc);
                    break;
                case "FC Porto":
                    c.setImage(R.drawable.fc_porto);
                    break;
                case "Paris Saint Germain FC":
                    c.setImage(R.drawable.paris_saint_germain_fc);
                    break;
                case "Olympique Lyonnais":
                    c.setImage(R.drawable.olympique_de_marseille);
                    break;
                case "AC Milan":
                    c.setImage(R.drawable.ac_milan);
                    break;
                case "Manchester City FC":
                    c.setImage(R.drawable.manchester_city_fc);
                    break;
                case "Sevilla FC":
                    c.setImage(R.drawable.sevilla_fc);
                    break;
                case "AS Monaco FC":
                    c.setImage(R.drawable.as_monaco_fc);
                    break;
                case "AS Roma":
                    c.setImage(R.drawable.as_roma);
                    break;
                case "Arsenal FC":
                    c.setImage(R.drawable.arsenal_fc);
                    break;
                case "BV Borussia Dortmund":
                    c.setImage(R.drawable.bv_borussia_dortmund);
                    break;
                case "Bayer 04 Leverkusen":
                    c.setImage(R.drawable.bayer_04_leverkusen);
                    break;
                case "Chelsea FC":
                    c.setImage(R.drawable.chelsea_fc);
                    break;
                case "Club Atletico de Mardid":
                    c.setImage(R.drawable.club_atletico_de_madrid);
                    break;
                case "Feyenoord":
                    c.setImage(R.drawable.feyenoord);
                    break;
                case "Juventus FC":
                    c.setImage(R.drawable.juventus_fc);
                    break;
                case "Manchester United FC":
                    c.setImage(R.drawable.manchester_united_fc);
                    break;
                case "Olympique de Marseille":
                    c.setImage(R.drawable.olympique_de_marseille);
                    break;
                case "PSV Eindhoven":
                    c.setImage(R.drawable.psv_eindhoven);
                    break;
                case "SSC Napoli":
                    c.setImage(R.drawable.ssc_napoli);
                    break;
                case "Sporting CP":
                    c.setImage(R.drawable.sporting_cp);
                    break;
            }*/

            c.setImage(images[i%images.length]);

            clubs.add(c);
        }

        ClubArrayAdapter adapter = new ClubArrayAdapter(this, clubs);
        list1.setAdapter(adapter);
        //list1.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //CheckedTextView checkedTextView = (CheckedTextView)view;
                //boolean checked = checkedTextView.isChecked();
                Toast.makeText(MainActivity.this, "You clicked on " + clubNames[position], Toast.LENGTH_SHORT).show();

                clubs.get(position).flipSelected();
            }
        });
    }
}
