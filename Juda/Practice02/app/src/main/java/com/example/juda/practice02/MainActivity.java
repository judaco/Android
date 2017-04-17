package com.example.juda.practice02;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

import static android.R.attr.name;

public class MainActivity extends AppCompatActivity {

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCream = (CheckBox)findViewById(R.id.whipped_cream);
        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate);
        EditText clientName = (EditText)findViewById(R.id.client_name);
        String name = clientName.getText().toString();
        boolean hasChocolate = chocolate.isChecked();
        boolean hasWhwhippedCream = whippedCream.isChecked();
        //Log.d("MainActivity", "Has whipped cream: ", hasWhwhippedCream);
        int price = calculatePrice(hasWhwhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(name ,price, hasWhwhippedCream, hasChocolate);

//        Intent intent = new Intent(Intent.ACTION_SENDTO);
//        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
//        intent.putExtra(Intent.EXTRA_SUBJECT, "Email test" + name);
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            startActivity(intent);
//        }

        displayMessage(priceMessage);
    }

    /**
     * Calculates the price of the order.
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int coffeePrice = 5;

        if (addWhippedCream) {
            coffeePrice += 1;
        }
        if (addChocolate)
            coffeePrice += 2;
        return  quantity * coffeePrice;
    }

    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate) {
        String priceMessage = "Name: " + name;
        priceMessage += "\n Add whipped cream? - " + addWhippedCream;
        priceMessage += "\n Add chocolate? - " + addChocolate;
        priceMessage += "\n Quantity: " + quantity;
        priceMessage += "\n Total: $" + price;
        priceMessage += "\n Thank you!";
        return priceMessage;
    }

    public void increment (View view) {
        if (quantity == 100){
            Toast.makeText(this, "Cannot order more than 100 cups of coffee", Toast.LENGTH_SHORT).show();
        }
        quantity += 1;
        displayQuantity(quantity);
    }

    public void decrement (View view){
        if (quantity == 1){
            Toast.makeText(this, "Cannot order less than 1 cup of coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity -= 1;
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }

//    /**
//     * This method displays the given price on the screen.
//     */
//    private void displayPrice(int number) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.price_text_view);
//        orderSummaryTextView.setText(NumberFormat.getCurrencyInstance().format(number));
//    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}