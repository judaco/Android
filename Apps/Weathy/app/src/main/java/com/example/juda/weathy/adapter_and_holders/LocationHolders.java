package com.example.juda.weathy.adapter_and_holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.juda.weathy.LocationObject;
import com.example.juda.weathy.R;

import java.util.List;

/**
 * Created by Juda on 05/08/2017.
 */

public class LocationHolders extends RecyclerView.ViewHolder {
    private static final String TAG = LocationHolders.class.getSimpleName();
    public TextView locationCity;
    public TextView weatherInfo;
    public TextView deleteText;
    public RadioButton selection;

    public LocationHolders (final View view, final List<LocationObject> locationObject) {
        super(view);
    }
}
