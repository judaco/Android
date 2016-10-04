package com.example.juda.passbetact2actopen;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class First extends Activity {

    private static final int REQUEST_CODE = 100;
    public static final String GO_TO = "GO_TO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
    }

    public void btnGoToThird(View view) {
        goTO(3);
    }

    public void btnGoToSecond(View view) {
        goTO(2);
    }

    private void goTO(int actNumber){
        Intent intent = new Intent(this, actNumber == 2 ? Second.class : Third.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE){
            if (resultCode == RESULT_OK){
                int goTO = data.getIntExtra(GO_TO, -1);
                switch (goTO){
                    case 2:
                        goTO(2);
                        break;
                    case 3:
                        goTO(3);
                        break;
                }
            }
        }
    }
}
