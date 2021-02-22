package com.tata.shoppersden.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Serializable {
    private LocalDate date;
    private String imgUrl;
    private String productDescription;
    private int pid;
    private String productName;
    private float price;
    private int quantity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return pid == product.pid && Float.compare(product.price, price) == 0 && quantity == product.quantity && Objects.equals(date, product.date) && Objects.equals(imgUrl, product.imgUrl) && Objects.equals(productDescription, product.productDescription) && Objects.equals(productName, product.productName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, imgUrl, productDescription, pid, productName, price, quantity);
    }
}
