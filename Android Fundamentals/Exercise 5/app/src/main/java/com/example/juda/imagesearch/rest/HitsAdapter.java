package com.example.juda.imagesearch.rest;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.juda.imagesearch.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Juda on 02/12/2017.
 */

public class HitsAdapter extends RecyclerView.Adapter<HitsAdapter.HitViewHolder> {

    private ArrayList<Hit> hits;

    public HitsAdapter(ArrayList<Hit> hitsList) {
        this.hits = hitsList;
    }

    @Override
    public HitViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.hit_layout, parent, false);
        ImageView hitImage = view.findViewById(R.id.hit_image);
        return new HitViewHolder(view, hitImage);
    }

    @Override
    public void onBindViewHolder(HitViewHolder holder, int position) {
        Hit hit = hits.get(position);
        Picasso.with(holder.hitLayout.getContext())
                .load(hit.getWebformatURL())
                .placeholder(R.drawable.loading)
                .into(holder.hitImage);
    }

    @Override
    public int getItemCount() {
        return hits.size();
    }

    class HitViewHolder extends RecyclerView.ViewHolder {

        View hitLayout;
        ImageView hitImage;

        public HitViewHolder(View itemView, ImageView imageView) {
            super(itemView);
            this.hitLayout = itemView;
            this.hitImage = imageView;
        }
    }
}
