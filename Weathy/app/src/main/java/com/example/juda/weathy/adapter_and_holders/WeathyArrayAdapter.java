package com.example.juda.weathy.adapter_and_holders;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.example.juda.weathy.JsonObjectList;
import com.example.juda.weathy.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static android.R.attr.resource;

/**
 * Created by Juda on 28/07/2017.
 */

public class WeathyArrayAdapter extends ArrayAdapter<JsonObjectList> {

    private final String DEBUG_TAG = "JsonObjectListAdapter";
    private int resourceIdView;
    private List<JsonObjectList> items;
    private List<JsonObjectList> allItems;
    private List<JsonObjectList> recommendations;

    public WeathyArrayAdapter(@NonNull Context context, int resourceIdView,
                              List<JsonObjectList> items) {
        super(context, resourceIdView, items);
        this.resourceIdView = resourceIdView;
        this.items = items;
        this.allItems = new ArrayList<>(this.items);
        this.recommendations = new ArrayList<JsonObjectList>();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(resourceIdView, null);
        }
        JsonObjectList jsonObjectList = items.get(position);
        if (jsonObjectList != null) {
            TextView jsonObjectListLabel = (TextView)view.findViewById(R.id.);
            if (jsonObjectListLabel != null) {
                jsonObjectListLabel.setText(jsonObjectList.getName() + "," + jsonObjectList.getCountry());;
            }
        }
        return view;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            String s = ((JsonObjectList)(resultValue)).getName();
            return s;
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            if (charSequence != null) {
                if (charSequence.length() == 4) {
                    for (JsonObjectList jsonObjectList : allItems) {
                        if (jsonObjectList.getName().toLowerCase().startsWith(charSequence.toString().toLowerCase())) {
                            recommendations.add(jsonObjectList);
                        }
                    }
                }
                if (charSequence.length() >= 4) {
                    List<JsonObjectList> newInfo = new ArrayList<>(recommendations);
                    recommendations.clear();
                    for (JsonObjectList newList : newInfo) {
                        if (newList.getName().toLowerCase().startsWith(charSequence.toString().toLowerCase())) {
                            recommendations.add(newList);
                        }
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = recommendations;
                filterResults.count = recommendations.size();
                System.out.println("new counting " + recommendations.size());
                return filterResults;
            } else {
                recommendations.clear();
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            List<JsonObjectList> filteredList = (List<JsonObjectList>) filterResults.values;
            if (filterResults != null && filterResults.count > 0) {
                clear();
                for (Iterator<JsonObjectList> data = filteredList.iterator();
                        data.hasNext();) {
                    JsonObjectList jsonObjectObtained = data.next();
                    add(jsonObjectObtained);
                }
                notifyDataSetChanged();
            }
        }
    };
}
