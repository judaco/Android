package com.example.juda.weathy;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;

/**
 * Created by Juda on 24/06/2017.
 */

class WeathyAdapter extends RecyclerView.Adapter<WeathyAdapter.WeathyAdapterViewHolder> {

    private static final int VIEW_TODAY = 0;
    private static final int VIEW_FUTURE = 1;

    private final Context contexto;

    final private WeathyAdapterOnClickHandler handler;

    public interface WeathyAdapterOnClickHandler {
        void onClick(long date);
    }

    private boolean setTodayLayout;

    private Cursor cursor;

    public WeathyAdapter(@NonNull Context context, WeathyAdapterOnClickHandler clickItem) {
        contexto = context;
        handler = clickItem;
        setTodayLayout = contexto.getResources().getBoolean(R.bool.use_today_layout);
    }

    @Override
    public WeathyAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId;

        switch (viewType) {
            case VIEW_TODAY: {
                layoutId = R.layout.weathy_today_list_item;
                break;
            }
            case VIEW_FUTURE: {
                layoutId = R.layout.weathy_list_item;
                break;
            }
            default:
                throw new IllegalArgumentException("Invalid view type " + viewType);
        }

        View view = LayoutInflater.from(contexto).inflate(layoutId, parent, false);
        view.setFocusable(true);
        return new WeathyAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WeathyAdapterViewHolder holder, int position) {

        cursor.moveToPosition(position);
        int weatherId = cursor.getInt(MainActivity.INDEX_WEATHER_CONDITION_ID);
        int imageId;

        int viewType = getItemViewType(position);

        switch (viewType) {
            case VIEW_TODAY:
                imageId = WeatherConvert.getLargeArtResourceIdForWeatherCondition(weatherId);
                break;
            case VIEW_FUTURE:
                imageId = WeatherConvert.getSmallArtResourceIdForWeatherCondition(weatherId);
                break;
            default:
                throw new IllegalArgumentException("Invalid view type " + viewType);
        }

        holder.icon.setImageResource(imageId);

        long dateInMillis = cursor.getLong(MainActivity.INDEX_WEATHER_DATE);
        String dateString = DateConvert.dateConvertToString(contexto, dateInMillis, false);
        holder.date.setText(dateString);

        String description = WeatherConvert.getStringForWeatherCondition(contexto, weatherId);
        String descriptionAccess = contexto.getString(R.string.access_forecast, description);
        holder.description.setText(description);
        holder.description.setContentDescription(descriptionAccess);

        double highTempCelsius = cursor.getDouble(MainActivity.INDEX_WEATHER_TEMP_MAX);
        String highString = WeatherConvert.formatTemp(contexto, highTempCelsius);
        String highAccess = contexto.getString(R.string.access_temp_high, highString);
        holder.tempHigh.setText(highString);
        holder.tempHigh.setContentDescription(highAccess);

        double lowTempCelsius = cursor.getDouble(MainActivity.INDEX_WEATHER_TEMP_MIN);
        String lowString = WeatherConvert.formatTemp(contexto, lowTempCelsius);
        String lowAAccess = contexto.getString(R.string.access_temp_low, lowString);
        holder.tempLow.setText(lowString);
        holder.tempLow.setContentDescription(lowAAccess);

    }

    @Override
    public int getItemCount() {
        if (cursor == null)
        return 0;
        return cursor.getCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (setTodayLayout && position == 0) {
            return VIEW_TODAY;
        } else {
            return VIEW_FUTURE;
        }
    }

    void swapCursor(Cursor newCursor) {
        cursor = newCursor;
        notifyDataSetChanged();
    }

    class WeathyAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        final TextView date;
        final TextView description;
        final TextView tempHigh;
        final TextView tempLow;
        final ImageView icon;


        public WeathyAdapterViewHolder(View itemView) {
            super(itemView);
            date = (TextView)itemView.findViewById(R.id.date);
            description = (TextView)itemView.findViewById(R.id.description);
            tempHigh = (TextView)itemView.findViewById(R.id.temp_high);
            tempLow = (TextView)itemView.findViewById(R.id.temp_low);
            icon = (ImageView)itemView.findViewById(R.id.weather_icon);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            cursor.moveToPosition(position);
            long dateInMillis = cursor.getLong(MainActivity.INDEX_WEATHER_DATE);
            handler.onClick(dateInMillis);
        }
    }
}
