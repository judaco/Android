package com.example.juda.sqlite;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    DBAdapter dbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbAdapter = new DBAdapter(this);
        dbAdapter.open();
        long person1 = dbAdapter.insertPerson("Juda", "Cossa", 1986);
        long person2 = dbAdapter.insertPerson("Paolo", "Maldini", 1972);
        dbAdapter.close();

        Log.d("Juda", "person1" + person1);
        Log.d("Juda", "person2" + person2);

        dbAdapter.open();
        Cursor c = null;
        try {
            c = dbAdapter.getPersons();
            while (c.moveToNext()) {//she is taking the cursor to the next name, but return boolean for the next person,
                // so after the last person it will be false - first one has nCore (default)
                int personId = c.getInt(0);
                String firstName = c.getString(1);
                String lastName = c.getString(2);
                int birthYear = c.getInt(3);
                Log.d("Juda", personId + " " + firstName + " " + lastName + " " + birthYear);
            }
        } finally {
            if (c == null)
                c.close();
        }
        dbAdapter.close();
    }
}
