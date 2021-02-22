package com.tata.shoppersden.models;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class ShoppingCart implements Serializable {
    private int cartId;
    private String cartName;
    private float price;
    private List<Product> productList;

    public ShoppingCart() {
        productList = new ArrayList<Product>();
    }

    public void addProduct(Product product) {
        System.out.println("here? really");
        productList.add(product);
    }

    public void removeProduct(int id) {
        int idx = -1, counter = 0;
        for(Product product: productList) {
            if (product.getPid() == id) {
                idx = counter;
            }
            counter++;
        }
        productList.remove(idx);
    }

    public void viewProducts() {
        for(Product product: productList) {
            System.out.println(product);
        }
    }

    public int getCountOfProducts() {
        return productList.size();
    }
}
