package com.example.juda.ourowntimepicker;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Juda on 29/10/2016.
 */

public class MyTimePicker extends LinearLayout {

    Context context;
    TextView txtHour;
    TextView txtMinute;

    int hour;
    int minute;

    public MyTimePicker(Context context) {
        super(context);
        this.context = context;
        super.setOrientation(HORIZONTAL);
        LinearLayout colLeft = new LinearLayout(context);
        LinearLayout colMiddle = new LinearLayout(context);
        LinearLayout colRight = new LinearLayout(context);

        colLeft.setOrientation(VERTICAL);
        colMiddle.setOrientation(VERTICAL);
        colRight.setOrientation(VERTICAL);

        LayoutParams layoutParams = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        LayoutParams layoutParamsMiddle = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.weight = 1;

        this.addView(colLeft, layoutParams);
        this.addView(colMiddle, layoutParamsMiddle);
        this.addView(colRight, layoutParams);

        Button btnPlusHour = new Button(context);
        btnPlusHour.setText("+");
        btnPlusHour.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                MyTimePicker.this.txtHour.setText(Integer.toString(++MyTimePicker.this.hour));
            }
        });

        LayoutParams layoutParamsButton = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        layoutParamsButton.weight = 1;
        colLeft.addView(btnPlusHour, layoutParamsButton);

        txtHour = new TextView(context);
        txtHour.setText("00");
        txtHour.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        LayoutParams layoutParamsTextView = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        colLeft.addView(new View(context), layoutParamsButton);
        colLeft.addView(txtHour, layoutParamsTextView);
        colLeft.addView(new View(context), layoutParamsButton);

        Button btnMinusHour = new Button(context);
        btnMinusHour.setText("-");
        btnMinusHour.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                MyTimePicker.this.txtHour.setText(Integer.toString(--MyTimePicker.this.hour));
            }
        });

        colLeft.addView(btnMinusHour, layoutParamsButton);
        TextView txtColon = new TextView(context);
        txtColon.setText(" : ");
        LayoutParams layoutParamsSpaceView = new LayoutParams(0,0);
        layoutParamsSpaceView.weight = 1;

        colMiddle.addView(new View(context), layoutParamsSpaceView);
        LayoutParams layoutParmasColon = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        colMiddle.addView(txtColon, layoutParmasColon);
        colMiddle.addView(new View(context), layoutParamsSpaceView);

        Button btnPlusMinute = new Button(context);
        btnPlusMinute.setText("+");
        btnPlusMinute.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                MyTimePicker.this.txtMinute.setText(Integer.toString(++MyTimePicker.this.minute));
            }
        });

        colRight.addView(btnPlusMinute, layoutParamsButton);
        txtMinute = new TextView(context);
        txtMinute.setText("00");
        txtMinute.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        colRight.addView(new View(context), layoutParamsButton);
        colRight.addView(txtMinute, layoutParamsTextView);
        colRight.addView(new View(context), layoutParamsButton);

        Button btnMinusMinute = new Button(context);
        btnMinusMinute.setText("-");
        btnMinusMinute.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                minute--;
                if (minute == -1){
                    minute = 59;
                    hour--;
                    if (hour == -1)
                        hour = 23;
                    txtHour.setText((hour < 10 ? "0" : "") + Integer.toString(hour));
                }
                    txtMinute.setText((minute < 10 ? "0" : "") + Integer.toString(minute));
            }
        });

        colRight.addView(btnMinusMinute, layoutParamsButton);
    }

    @Override
    public void setOrientation(int orientation) {
        super.setOrientation(orientation);
    }
}
