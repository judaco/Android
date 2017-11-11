package juda.cityguide;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button learnBtn = findViewById(R.id.learn_more);
    }

    public void btnLearn(View view) {

        String url = "https://en.wikipedia.org/wiki/Jerusalem";
        Intent browser = new Intent(Intent.ACTION_VIEW);
        browser.setData(Uri.parse(url));
        startActivity(browser);
    }
}
