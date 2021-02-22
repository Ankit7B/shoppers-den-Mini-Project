package com.tata.shoppersden.model;

import com.tata.shoppersden.models.Product;
import com.tata.shoppersden.models.ShoppingCart;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class ShoppingCartTests {
    private static ShoppingCart shoppingCart;
    private Product product;
    @BeforeAll
    static void initialize() {
        shoppingCart = new ShoppingCart();
    }

    @BeforeEach
    public void generateProduct() {
        product = new Product();
        product.setProductName("Iphone " + new Random().nextInt(100));
        product.setProductDescription("Best Phone ?");
        product.setDate(LocalDate.now());
        product.setPrice(new Random().nextFloat() * 100000.00f);
        product.setImgUrl("some url");
        product.setQuantity(new Random().nextInt(30) + 1);
    }

    @BeforeEach
    public void addProduct() {
        int init = shoppingCart.getCountOfProducts();
        shoppingCart.addProduct(product);
        assertEquals(init + 1, shoppingCart.getCountOfProducts());
    }

    @Test
    public void viewAllProducts() {
        shoppingCart.viewProducts();
        assertTrue(true);
    }

    @Test
    public void removeProduct() {
        int init = shoppingCart.getCountOfProducts();
        shoppingCart.removeProduct(0);
        assertEquals(init - 1, shoppingCart.getCountOfProducts());
    }

}
