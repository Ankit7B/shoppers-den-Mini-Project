package com.tata.shoppersden.Dao.tests;

import com.tata.shoppersden.dao.ShoppingCartDao;
import com.tata.shoppersden.dao.ShoppingCartDaoImpl;
import com.tata.shoppersden.models.Product;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Random;

public class ShoppingCartDaoTests {
    private static ShoppingCartDao shoppingCartDaoImpl;

    @BeforeAll
    public static void initialize() {
        shoppingCartDaoImpl = new ShoppingCartDaoImpl();
    }

    @Test
    public void firstTest() {
        Product product = new Product();
        product.setProductName("Iphone " + new Random().nextInt(100));
        product.setProductDescription("Best Phone ?");
        product.setDate(LocalDate.now());
        product.setPrice(new Random().nextFloat() * 100000.00f);
        product.setImgUrl("some url");
        product.setQuantity(new Random().nextInt(30) + 1);
//        shoppingCartDaoImpl.addProductToCart(product);
//        shoppingCartDaoImpl.saveState();
    }

    @Test
    public void testState() {

    }
}
