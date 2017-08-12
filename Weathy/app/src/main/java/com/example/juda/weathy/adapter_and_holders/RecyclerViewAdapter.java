package com.example.juda.weathy.adapter_and_holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.juda.weathy.R;
import com.example.juda.weathy.WeathyActivity;
import com.example.juda.weathy.WeathyObject;

import java.util.List;

/**
 * Created by Juda on 05/08/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecylerViewHolders> {

    private List<WeathyObject> daily;
    protected Context context;

    public RecyclerViewAdapter(WeathyActivity weathyActivity, List<WeathyObject> weekDays) {
    }

    @Override
    public RecylerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        RecylerViewHolders viewHolders = null;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.id., parent, false);
        viewHolders = new RecylerViewHolders(view);
        return viewHolders;
    }

    @Override
    public void onBindViewHolder(RecylerViewHolders holder, int position) {
        holder.day.setText(daily.get(position).);
    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
