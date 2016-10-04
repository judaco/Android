package com.example.juda.passbetact2actopen;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Second extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    public void btnGoToFirst(View view) {
        goTO(1);
    }

    public void btnGoToThird(View view) {
        goTO(3);
    }

    private void goTO (int actNumber){
        Intent data = new Intent();
        data.putExtra(First.GO_TO, actNumber);
        setResult(RESULT_OK, data);
        finish();
    }
}
