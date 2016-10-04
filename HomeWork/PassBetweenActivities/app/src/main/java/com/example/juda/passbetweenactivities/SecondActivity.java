package com.example.juda.passbetweenactivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Juda on 24/09/2016.
 */

public class SecondActivity extends Activity {
    private static final int REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        int goTO = getIntent().getIntExtra(FirstActivity.GO_TO, -1);
        if (goTO == 3){
            goTo3rd();
        }
    }

    private void goTo3rd(){
        Intent intent = new Intent(this, ThirdActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    public void btnGoToFirstActivity(View view) {
        finish();
    }

    public void btnGoToThirdActivity(View view) {
        goTo3rd();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE){
            if (resultCode == RESULT_OK){
                int goTO = data.getIntExtra(FirstActivity.GO_TO, -1);
                if (goTO ==1)
                    finish();
            }
        }
    }
}
