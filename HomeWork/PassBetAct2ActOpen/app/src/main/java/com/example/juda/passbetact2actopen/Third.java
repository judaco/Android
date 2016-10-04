package com.example.juda.passbetact2actopen;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Third extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
    }

    public void btnGoToFirst(View view) {
        goTO(1);
    }

    public void btnGoToSecond(View view) {
        goTO(2);
    }

    private void goTO(int actNumber){
        Intent data = new Intent();
        data.putExtra(First.GO_TO, actNumber);
        setResult(RESULT_OK, data);
        finish();
    }
}
