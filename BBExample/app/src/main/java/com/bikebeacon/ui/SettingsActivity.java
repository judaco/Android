package com.bikebeacon.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juda.bbexample.R;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

public class SettingsActivity extends AppCompatActivity {

    Switch lowPowerMode;
    Switch mapDraw;
    TextView statRefreshTimeTv;
    DiscreteSeekBar statRefreshTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        lowPowerMode = (Switch)findViewById(R.id.low_power_mode);
        mapDraw = (Switch)findViewById(R.id.map_draw);
        statRefreshTimeTv = (TextView)findViewById(R.id.stat_refresh_time_tv);
        statRefreshTime = (DiscreteSeekBar)findViewById(R.id.stat_refresh_time);

        lowPowerMode.setChecked(true);
        lowPowerMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    Toast.makeText(getApplicationContext(), "Low Power Mode is ON", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Low Power Mode is OFF", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mapDraw.setChecked(true);
        mapDraw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {

                } else {

                }
            }
        });

        statRefreshTime.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

            }
        });
    }
}
