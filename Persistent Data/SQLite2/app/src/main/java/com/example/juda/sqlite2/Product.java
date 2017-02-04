package com.example.juda.sqlite2;

/**
 * Created by Juda on 04/02/2017.
 */

public class Product {
    long productId;
    String name;
    int categoryId;
    float unitPrice;
    int unitsInStock;
    boolean discontinued;

    public Product(long productId, String name,
                   int categoryId, float unitPrice,
                   int unitsInStock, boolean discontinued) {
        this.productId = productId;
        this.name = name;
        this.categoryId = categoryId;
        this.unitPrice = unitPrice;
        this.unitsInStock = unitsInStock;
        this.discontinued = discontinued;
    }
}
