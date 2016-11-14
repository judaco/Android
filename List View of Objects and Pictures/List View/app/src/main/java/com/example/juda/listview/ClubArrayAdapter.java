package com.example.juda.listview;

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
 * Created by Juda on 12/11/2016.
 */

public class ClubArrayAdapter extends ArrayAdapter<Club> {

    private Activity activity;
    private List<Club> clubs;

    public ClubArrayAdapter(Activity activity, List <Club> clubs) {
        super(activity, R.layout.item_club);
        this.activity = activity;
        this.clubs = clubs;
    }

    //Recycle Views (from top and to bootom)
    static class ViewContainer {
        ImageView imgClubs;
        TextView txtClub;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewContainer viewContainer;
        View view = convertView;

        // if created for the first time
        if (view == null){
            LayoutInflater layoutInflater = activity.getLayoutInflater();
            view = layoutInflater.inflate(R.layout.item_club,null, true);
            viewContainer = new ViewContainer();
            viewContainer.txtClub = (TextView) view.findViewById(R.id.txtClub);
            viewContainer.imgClubs = (ImageView) view.findViewById(R.id.imgClub);
            view.setTag(viewContainer);
        }else {
            viewContainer  = (ViewContainer) view.getTag();
        }

        viewContainer.txtClub.setText(clubs.get(position).getName());
        viewContainer.imgClubs.setImageResource(clubs.get(position).getImage());

        return view;
    }
}
