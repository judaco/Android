package com.example.juda.gcd;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    EditText Num1;
    EditText Num2;
    Button btnGCD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Num1 = (EditText)findViewById(R.id.Num1);
        Num2 = (EditText)findViewById(R.id.Num2);
        btnGCD = (Button)findViewById(R.id.btnGCD);



        btnGCD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        }

        //public static int gcd(int a, int b) {
          //  return b == 0 ? a : gcd(b, a % b);
    public static int gcd (int a, int b){
        if (b == 0){
            return a;
        }
        return gcd(b, a%b);
    }

    public void btnCGD(View view) {
        Toast.makeText(this, "the result is:", Toast.LENGTH_SHORT).show();
    }
}
