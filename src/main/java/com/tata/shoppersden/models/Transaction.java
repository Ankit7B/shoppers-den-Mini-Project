package com.tata.shoppersden.models;

import lombok.Data;

@Data
public class Transaction {
    private int transactionId;
    private int cartId;
    private float totalPrice;
}
