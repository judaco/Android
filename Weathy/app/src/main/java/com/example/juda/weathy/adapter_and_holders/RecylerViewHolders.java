package com.example.juda.weathy.adapter_and_holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Juda on 05/08/2017.
 */

public class RecylerViewHolders extends RecyclerView.ViewHolder {

    private static final String TAG = RecylerViewHolders.class.getSimpleName();

    public TextView day;
    public ImageView weatherIcon;
    public TextView weatherResult;
    public TextView weatherResultSmall;

    public RecylerViewHolders(View itemView) {
        super(itemView);
    }
}
