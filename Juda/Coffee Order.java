package com.example.juda.practice02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import java.text.NumberFormat;

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
        boolean hasWhwhippedCream = whippedCream.isChecked();
        //Log.d("MainActivity", "Has whipped cream: ", hasWhwhippedCream);
        int price = calculatePrice();
        String priceMessage = createOrderSummary(price, hasWhwhippedCream);
        displayMessage(priceMessage);
    }

    /**
     * Calculates the price of the order.
     */
    private int calculatePrice() {
        return  quantity * 5;
    }

    private String createOrderSummary(int price, boolean addWhippedCream) {
        String priceMessage = "Name: Juda Cossa";
        priceMessage += "\n Add whipped cream? - " + addWhippedCream;
        priceMessage += "\n Quantity: " + quantity;
        priceMessage += "\n Total: $" + price;
        priceMessage += "\n Thank you!";
        return priceMessage;
    }

    public void increment (View view){
        quantity += 1;
        displayQuantity(quantity);
    }

    public void decrement (View view){
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