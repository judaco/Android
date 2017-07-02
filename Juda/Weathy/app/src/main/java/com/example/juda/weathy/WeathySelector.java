package com.example.juda.weathy;

import android.content.Intent;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class WeathySelector extends AppCompatActivity {

    private TextView weather;
    private String forecastDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weathy_selector);

        weather = (TextView)findViewById(R.id.weather);

        Intent intent = getIntent();

        if (intent != null){
            if (intent.hasExtra(Intent.EXTRA_TEXT)){
                forecastDay = intent.getStringExtra(Intent.EXTRA_TEXT);
                weather.setText(forecastDay);
            }
        }
    }

    private Intent sharingForecastDay() {
        Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setText(forecastDay)
                .getIntent();
        return shareIntent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.selector, menu);
        MenuItem menuItem = menu.findItem(R.id.slected_day_sharing);
        menuItem.setIntent(sharingForecastDay());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.settings){
            Intent openSettings = new Intent(this, Settings.class);
            startActivity(openSettings);
            }
        return super.onOptionsItemSelected(item);
    }
}
