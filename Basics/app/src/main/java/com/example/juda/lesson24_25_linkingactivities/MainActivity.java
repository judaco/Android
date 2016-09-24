package com.example.juda.lesson24_25_linkingactivities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ViewPropertyAnimatorCompatSet;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;

public class MainActivity extends Activity{

    public static final String FIRST_NAME = "firstName";
    public static final String AGE = "age";
    public static final int REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//act 'inflate' - nipuah - reading the xml file
    }

    public void btnClick (View view){
        Intent intent = new Intent(this, SecondActivity.class);//the intention to move a client to another Activity (screen)
        //intent.putExtra(FIRST_NAME, "Juda");
       //intent.putExtra(AGE, "29");

        Bundle extras = new Bundle();
        extras.putString(FIRST_NAME, "Juda");
        extras.putInt(AGE, 29);
        Person p = new Person("Juda", "Cossa", 29);
        extras.putSerializable("person", p);

        intent.putExtras(extras);//attach the Bundle object to the Intent object

        //startActivity(Intent.createChooser(intent, "Please Choose"));
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (REQUEST_CODE == requestCode){
            if (resultCode == RESULT_OK){
                Toast.makeText(this, "password: " + data.getStringExtra(SecondActivity.PASSWORD), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
