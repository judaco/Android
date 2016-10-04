package com.example.juda.passbetweenactivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Juda on 24/09/2016.
 */

public class ThirdActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
    }

    public void btnGoToFirstActivity(View view) {
        goTO(1);
    }

    public void btnGoToSecondActivity(View view) {
        goTO(2);
    }

    private void goTO(int activityNumber){
        Intent data = new Intent();
        data.putExtra(FirstActivity.GO_TO, activityNumber);
        setResult(RESULT_OK, data);
        finish();
    }
}
