package com.example.juda.exercise3.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.juda.exercise3.R;
import com.example.juda.exercise3.util.Club;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Juda on 18/11/2017.
 */

public class ClubAdapter extends RecyclerView.Adapter<ClubAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Club> mClubs;


    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView logo, trophy;
        public TextView clubName;

        public ViewHolder(View itemView) {
            super(itemView);
            logo = itemView.findViewById(R.id.li_club_img);
            clubName = itemView.findViewById(R.id.li_name_tv);
            trophy = itemView.findViewById(R.id.li_trophy_img);
        }
    }

    public ClubAdapter(Context context, ArrayList<Club> clubs) {
        mContext = context;
        mClubs = clubs;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //Create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.li_club, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //Get our custom object from our dataset at this position
        final Club club = mClubs.get(position);

        //Bind our views with our data
        holder.clubName.setText(club.getClubName());
        Picasso.with(mContext).load(club.getImageUrl()).into(holder.logo);
        //Display the trophy image if the club won it
        if (club.isChampionsLeagueWinner()){
            holder.trophy.setVisibility(View.VISIBLE);
        } else {
            holder.trophy.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mClubs.size();
    }
}
