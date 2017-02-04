package com.example.juda.sqlite2;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Juda on 04/02/2017.
 */

public class ProductFragment extends DialogFragment {


    int productId = 0;
    String productName;
    int categoryId;
    float unitPrice;
    int unitsInStock;
    boolean discontinued;


    EditText txtProductName, txtCategoryId,
            txtUnitPrice, txtUnitsInStock;
    CheckBox chkDiscontinued;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);

        txtProductName = (EditText)view.findViewById(R.id.txtProductName);
        txtCategoryId = (EditText)view.findViewById(R.id.txtCategoryId);
        txtUnitPrice = (EditText)view.findViewById(R.id.txtUnitPrice);
        txtUnitsInStock = (EditText)view.findViewById(R.id.txtUnitsInStock);
        chkDiscontinued = (CheckBox)view.findViewById(R.id.chkDiscontinued);



        if(productId != 0){
            TextView lblProductId = (TextView)view.findViewById(R.id.lblProductId);
            lblProductId.setText(String.valueOf(productId));
            txtProductName.setText(productName);
            txtCategoryId.setText(String.valueOf(categoryId));
            txtUnitPrice.setText(String.valueOf(unitPrice));
            txtUnitsInStock.setText(String.valueOf(unitsInStock));
            chkDiscontinued.setChecked(discontinued);
        }

        Button btnSave = (Button)view.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }
}
