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

        timePicker = (TimePicker)findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);
    }

    public void btnOnClick(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int minute = timePicker.getMinute();
        }else{
            timePicker.getCurrentMinute();
        }

        int hour = timePicker.getCurrentHour();
        int minute = timePicker.getCurrentMinute();

        String time =  hour < 10 ? "0" : "";
        time += hour + ":";
        if(minute < 10)
            time += "0";
        time += minute;

        NumberFormat formatter = new DecimalFormat("00");
        time = formatter.format(hour) + ":" + formatter.format(minute);

        Toast.makeText(MainActivity.this, time, Toast.LENGTH_SHORT).show();

    }
}
