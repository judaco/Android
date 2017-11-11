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
        RecylerViewHolders viewHolder = null;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_weather_list, parent, false);
        viewHolder = new RecylerViewHolders(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecylerViewHolders holder, int position) {
        holder.day.setText(daily.get(position).getDay());
        holder.weatherIcon.setImageResource(daily.get(position).getWeatherIcon());
        double temp = Double.parseDouble(daily.get(position).getWeatherResult());
        holder.weatherResult.setText(String.valueOf(Math.round(temp)) + "°");
        holder.weatherResultSmall.setText(daily.get(position).getSmallWeatherResult());
        holder.weatherResultSmall.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
