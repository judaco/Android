package com.example.juda.timepicker;

        import android.app.Activity;
        import android.os.Build;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.TimePicker;
        import android.widget.Toast;

        import java.text.DecimalFormat;
        import java.text.NumberFormat;

public class MainActivity extends Activity {

    private TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timePicker = (TimePicker) findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);
    }

    public void btnOnClick(View view) {
        int minute = 0;
        int hour = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            minute = timePicker.getMinute();
            hour = timePicker.getHour();
        } else {
            minute = timePicker.getCurrentMinute();
            hour = timePicker.getCurrentHour();
        }
        Toast.makeText(this, ((hour < 10) ? "0" : "") + hour + ":" + ((minute < 10) ? "0" : "") + minute,
                Toast.LENGTH_SHORT).show();
        NumberFormat numberFormat = new DecimalFormat("00");
        Toast.makeText(this, numberFormat.format(hour) + ":" +
                numberFormat.format(minute), Toast.LENGTH_SHORT).show();
    }
}
