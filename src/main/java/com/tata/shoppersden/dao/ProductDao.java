package com.tata.shoppersden.dao;

import com.tata.shoppersden.models.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao {
    public void addProductToDB(Product product) throws SQLException;
    public Product getProductById(int id);
    public Product getProductByName(String name);
    public boolean removeProductById(int id);
    public void modifyProductById(Product product);
    public List<Product> getAllProducts();
    public int getProductCount();
}
