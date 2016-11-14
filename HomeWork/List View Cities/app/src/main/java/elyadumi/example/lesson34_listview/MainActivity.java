package elyadumi.example.lesson34_listview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    String [] citiesNames = {
            "Kefar saba" ,
            "Rosh hhain",
            "Lod",
            "Nantnia",
            "Ranana",
            "Broklin",
            "Dimona",
            "Ber Sheva",
            "Shoam",
            "Bni Brak",
            "London",
            "USA",
            "Germany",
            "Armenia",
            "Finland"
    };
    //  1. anuter way to enter images into the list.
    int [] images = {R.drawable.air,R.drawable.fire,R.drawable.grass,R.drawable.water};

    ListView listView;
    List<City> cities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.lv_cities);
        cities = new ArrayList<>();
        for (int i = 0; i < citiesNames.length ; i++) {
            City c = new City(citiesNames[i],i%3==0,R.mipmap.ic_launcher);

            /*
                        switch (citiesNames[i]){
                case "Lod":
                    c.setImage(R.drawable.air);
                    break;
                case "USA":
                    c.setImage(R.drawable.fire);
                    break;
                case "Dimona":
                    c.setImage(R.drawable.grass);
                    break;
                case "Germany":
                    c.setImage(R.drawable.water);
                    break;
                case "Finland":
                    c.setImage(R.drawable.world);
                    break;
            }
             */

            // 1.
            c.setImage(images[i%images.length]);

            cities.add(c);
        }

        //
        //      ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
        //             android.R.layout.simple_list_item_1,cities);

        //    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
        //            android.R.layout.simple_list_item_checked,cities);

      // ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
      //          R.layout.item_city,R.id.txtCity,citiesNames);

        MyCityAdapter adapter = new MyCityAdapter(this,cities);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "your city is : " + citiesNames[position], Toast.LENGTH_SHORT).show();

                // CheckedTextView checked = (CheckedTextView) view;
                // if (checked.isChecked()){

                //  }
                cities.get(position).flipSelected();


            }
        });

        //   listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }
}
