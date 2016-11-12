package com.example.juda.listview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by Juda on 12/11/2016.
 */

public class ClubArrayAdapter extends ArrayAdapter<Club> {

    Context context;
    List<Club> clubs;

    public ClubArrayAdapter(Context context, List<Club> clubs) {
        super(context, R.layout.item_club);
        this.context = context;
        this.clubs = clubs;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}
