package com.tata.shoppersden.Dao.tests;

import com.tata.shoppersden.dao.ProductDaoImpl;
import com.tata.shoppersden.models.Product;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class ProductDaoTests {
    private Product product;
    private static ProductDaoImpl productDaoImpl;
    @BeforeAll
    public static void initialize() {
        productDaoImpl = new ProductDaoImpl();
    }

    @BeforeEach
    public void getInstance() {
        product = new Product();
        product.setProductName("Iphone " + new Random().nextInt(100));
        product.setProductDescription("Best Phone ?");
        product.setDate(LocalDate.now());
        product.setPrice(new Random().nextFloat() * 100000.00f);
        product.setImgUrl("some url");
        product.setQuantity(new Random().nextInt(30) + 1);
    }

    @RepeatedTest(10)
    public void testAddProductToDB() {
        int initCount = productDaoImpl.getProductCount();
        productDaoImpl.addProductToDB(product);
        int finalCount = productDaoImpl.getProductCount();
        assertEquals(initCount + 1, finalCount);
    }

    @Test
    public void testGetProductById() {
        Product product = productDaoImpl.getProductById(1);
        System.out.println(product);
        assertNotNull(product);
    }

    @Test
    public void testGetProductByName() {
        Product product = productDaoImpl.getProductByName("Iphone 62");
        System.out.println(product);
        assertNotNull(product);
    }

    @Test
    public void positiveTestRemoveProductById() {
        assertEquals(true, productDaoImpl.removeProductById(2));
    }

    @Test public void negativeTestRemoveProductById() {
        assertEquals(false, productDaoImpl.removeProductById(-1));
    }

    @Test
    public void testForGetAllProducts() {
        List<Product> receive = productDaoImpl.getAllProducts();
        for(Product product: receive)
            System.out.println(product);
        assertNotNull(receive);
    }
}
