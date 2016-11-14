package elyadumi.example.lesson34_listview;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by elyadumi on 13/11/2016.
 */

public class MyCityAdapter extends ArrayAdapter<City> {

    private Activity activity;
    private List<City> cities;


    public MyCityAdapter(Activity activity, List<City> cities) {
        super(activity, R.layout.item_city, cities);
        this.activity = activity;
        this.cities = cities;
    }

    // recycle Views.
    static class ViewContainer{
        ImageView imgCities;
        TextView txtCity;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewContainer viewContainer;
        View view = convertView;

        // if created for the firs time
        if (view == null){
            LayoutInflater layoutInflater = activity.getLayoutInflater();
            view = layoutInflater.inflate(R.layout.item_city,null);
            viewContainer = new ViewContainer();
            viewContainer.txtCity = (TextView) view.findViewById(R.id.txtCity);
            viewContainer.imgCities = (ImageView) view.findViewById(R.id.iv_city);
            view.setTag(viewContainer);
        }else {
            viewContainer  = (ViewContainer) view.getTag();
        }


        viewContainer.txtCity.setText(cities.get(position).getName());
        viewContainer.imgCities.setImageResource(cities.get(position).getImage());

        return view;
    }


}
