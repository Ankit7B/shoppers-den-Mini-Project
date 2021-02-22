package com.tata.shoppersden.dao;

import com.tata.shoppersden.models.Product;

public interface ShoppingCartDao {
    public void addProductToCart(Product product);
    public void removeProductFromCart(int id);
    public void viewAllProducts();
    public void pay();
    public int getCartId();
}