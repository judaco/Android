package com.example.juda.weathy;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Juda on 24/06/2017.
 */

public class WeathyAdapter extends RecyclerView.Adapter <WeathyAdapter.WeathyAdapterViewHolder> {

    private String [] weatherData;

    private final WeathyAdapterOnClickItem adapterOnClickItem;

    public interface WeathyAdapterOnClickItem {
        void onClick (String weatherDay);
    }

    public WeathyAdapter(WeathyAdapterOnClickItem clickItem) {
        adapterOnClickItem = clickItem;
    }

    public class WeathyAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public final TextView weather;

        public WeathyAdapterViewHolder(View itemView) {
            super(itemView);
            weather = (TextView)itemView.findViewById(R.id.weather_data);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            String weatherDay = weatherData[adapterPosition];
            adapterOnClickItem.onClick(weatherDay);
        }
    }
    @Override
    public WeathyAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int listItemId = R.layout.weathy_list_item;
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(listItemId, parent);
        return new WeathyAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WeathyAdapterViewHolder holder, int position) {
        String currentDayForecast = weatherData[position];
        holder.weather.setText(currentDayForecast);
    }

    @Override
    public int getItemCount() {
        if (weatherData == null)
        return 0;
        return weatherData.length;
    }

    public void setWeatherData(String[] weatherData) {
        this.weatherData = weatherData;
        notifyDataSetChanged();
    }
}
