package com.example.juda.weathy.adapter_and_holders;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.example.juda.weathy.DatabaseQuery;
import com.example.juda.weathy.LocationObject;
import com.example.juda.weathy.R;
import com.example.juda.weathy.RadioButtonObject;
import com.example.juda.weathy.WeathySharedPref;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juda on 05/08/2017.
 */

public class LocationAdapter extends RecyclerView.Adapter<LocationHolders> implements CompoundButton.OnCheckedChangeListener {
    private List<LocationObject> locationObjects;
    protected Context context;
    private List<RadioButtonObject> allRadioButton;
    private DatabaseQuery databaseQuery;
    int[] id = new int []{R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5};
    private WeathySharedPref sharedPref;
    public LocationAdapter(Context context, List<LocationObject> locationObjects) {
        this.locationObjects = locationObjects;
        this.context = context;
        allRadioButton = new ArrayList<RadioButtonObject>();
        databaseQuery = new DatabaseQuery(context);
        sharedPref = new WeathySharedPref(context);
    }

    @Override
    public LocationHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        LocationHolders viewHolder = null;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_location, parent, false);
        viewHolder = new LocationHolders(view, locationObjects);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final LocationHolders holder, final int position) {
        holder.locationCity.setText(Html.fromHtml(locationObjects.get(position).getLocCity()));
        holder.weatherInfo.setText(Html.fromHtml(locationObjects.get(position).getWeatherInfo()));
        holder.deleteText.setTag(R.id.TAG_KEY, String.valueOf(locationObjects.get(position).getId()));
        holder.deleteText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index = locationObjects.get(position).getId();
                if (!holder.selection.isChecked()) {
                    databaseQuery.deleteLocation(index);
                    locationObjects.remove(position);
                    notifyItemRemoved(position);
                }
            }
        });
        String buttonId = sharedPref.getLocationInPref();
        System.out.println("Stored id " + buttonId);
        holder.selection.setOnCheckedChangeListener(this);
        allRadioButton.add(new RadioButtonObject(holder.selection, locationObjects.get(position).getLocCity()));
        String storedLocation = sharedPref.getLocationInPref();
        if (allRadioButton.get(position).getButtonName().equals(storedLocation)){
            holder.selection.setChecked(true);
        }
    }

    @Override
    public int getItemCount() {
        return this.locationObjects.size();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
            RadioButton button = (RadioButton)compoundButton;
            int checkedRadioId = button.getId();
            for (int i = 0; i < allRadioButton.size(); i++) {
                if (allRadioButton.get(i).getButton().getId() != checkedRadioId){
                    allRadioButton.get(i).getButton().setChecked(false);
                } else {
                    String name = allRadioButton.get(i).getButtonName();
                    sharedPref.setLocationInPref(name);
                }
            }
        }
    }

    private void setRadioButtonId(RadioButton button, int position){
        if(position == 0){
            button.setId(id[position]);
        }
        if(position == 1){
            button.setId(id[position]);
        }
        if(position == 2){
            button.setId(id[position]);
        }
        if(position == 3){
            button.setId(id[position]);
        }
        if(position == 4){
            button.setId(id[position]);
        }
    }
}