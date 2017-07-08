package com.example.juda.weathy;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Juda on 24/06/2017.
 */

class WeathyAdapter extends RecyclerView.Adapter<WeathyAdapter.WeathyAdapterViewHolder> {

    private final Context contexto;

    final private WeathyAdapterOnClickHandler handler;

    public interface WeathyAdapterOnClickHandler {
        void onClick(String weatherDay);
    }

    private Cursor cursor;

    public WeathyAdapter(@NonNull Context context, WeathyAdapterOnClickHandler clickItem) {
        contexto = context;
        handler = clickItem;
    }

    @Override
    public WeathyAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(contexto).inflate(R.layout.weathy_list_item, parent, false);
        view.setFocusable(true);
        return new WeathyAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WeathyAdapterViewHolder holder, int position) {
        cursor.moveToPosition(position);

        long dateInMillis = cursor.getLong(MainActivity.INDEX_WEATHER_DATE);
        String dateString = DateConvert.getFriendlyDateString(contexto, dateInMillis, false);
        int weatherId = cursor.getInt(MainActivity.INDEX_WEATHER_CONDITION_ID);
        String description = WeatherConvert.getStringForWeatherCondition(contexto, weatherId);
        double highTempCelsius = cursor.getDouble(MainActivity.INDEX_WEATHER_TEMP_MAX);
        double lowTempCelsius = cursor.getDouble(MainActivity.INDEX_WEATHER_TEMP_MIN);

        String highLowTemp = WeatherConvert.formatHighAndLowTemperatures(contexto, highTempCelsius
        , lowTempCelsius);
        String weatherShow = dateString + " - " + description + " - " + highLowTemp;

        holder.weather.setText(weatherShow);

    }

    @Override
    public int getItemCount() {
        if (cursor == null)
        return 0;
        return cursor.getCount();
    }

    void swapCursor(Cursor newCursor) {
        cursor = newCursor;
        notifyDataSetChanged();
    }

    class WeathyAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        final TextView weather;

        public WeathyAdapterViewHolder(View itemView) {
            super(itemView);
            weather = (TextView)itemView.findViewById(R.id.weather_data);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            String weatherDay = weather.getText().toString();
            handler.onClick(weatherDay);
        }
    }
}
