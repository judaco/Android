package com.example.juda.sqlite2;

import android.app.Activity;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Juda on 04/02/2017.
 */

public class ProductsCursorAdapter extends BaseAdapter {

    Cursor cursor;
    Activity activity;

    public ProductsCursorAdapter(Activity activity){
        this.activity = activity;
        //we assume Database is started and opened
        cursor = Database.getAllProducts();
    }


    @Override
    public int getCount() {
        return Database.getProductsCount();
    }

    @Override
    public Object getItem(int position) {
        if(cursor.moveToPosition(position)){
            Product product = new Product(
                    cursor.getLong(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getFloat(3),
                    cursor.getInt(4),
                    cursor.getInt(5)==1
            );
            return product;
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        if(cursor.moveToPosition(position)){
            return cursor.getLong(0);
        }
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_product, parent, false);
        TextView lblProductName =
                (TextView) view.findViewById(R.id.lblProductName);
        if(cursor.moveToPosition(position)) {
            lblProductName.setText(cursor.getString(1));
        }
        return view;
    }
}
