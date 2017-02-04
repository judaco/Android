package com.example.juda.sqlite2;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

 ListView listProducts;
  ProductsCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Database.start(this);
        Database.openRead();

        listProducts = (ListView)findViewById(R.id.listProducts);
        adapter = new ProductsCursorAdapter(this);

        /*long millisecondsInDay = 1000L * 60L * 60L * 24L;
        long now = System.currentTimeMillis();
        int days = (int)(now / millisecondsInDay);
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTimeInMillis(now);
        int year = calendar.get(Calendar.YEAR);*/


        //Database.openWrite();
        /*
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < 1000; i++) {
            Database.addProduct(
                    "item"+i,
                    random.nextInt(10),
                    random.nextFloat()*200,
                    random.nextInt(1000),
                    false);
        }
        Database.close();
        Database.finish();*/

       /* Database.openWrite();
        Database.addProduct("screen", 1, 1992.9f, 2000, false);
        Database.close();*/


        /*
        Database.openWrite();
        Database.updateProduct(2, "red baloon", 2, 13.4f, 13, false);
        Database.close();
        */

        /*
        Database.openWrite();
        Database.deleteProduct(2);
        Database.close();
        */
        /*Database.openRead();
        Cursor cursor = Database.getAllProducts();
        while (cursor.moveToNext()){
            int productId = cursor.getInt(0);
            String productName = cursor.getString(1);
            int categoryId = cursor.getInt(2);
            float unitPrice = cursor.getFloat(3);
            int unitsInStock = cursor.getInt(4);
            boolean discontinued = cursor.getInt(5) == 1;
            Log.d("Juda", "product: " + productId + " " + productName + " "
                + categoryId + " " + unitPrice + " " + unitsInStock +
                " " + discontinued);
        }
        Database.close();*/

      listProducts.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Database.close();
        Database.finish();
    }
}
