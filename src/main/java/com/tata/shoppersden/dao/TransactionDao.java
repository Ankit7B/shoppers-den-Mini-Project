package com.tata.shoppersden.dao;

import com.tata.shoppersden.models.ShoppingCart;

public interface TransactionDao {
    public void pay(ShoppingCart cart);
}
