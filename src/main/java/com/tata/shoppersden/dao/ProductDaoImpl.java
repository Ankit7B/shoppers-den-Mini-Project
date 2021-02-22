package com.tata.shoppersden.dao;

import com.tata.shoppersden.helpers.PostgresConnectionHelper;
import com.tata.shoppersden.models.Product;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProductDaoImpl implements ProductDao {
    private Connection conn;
    private PreparedStatement addProductPre, getProductByIdPre, getProductByNamePre, removeProductByIdPre, modifyProductPre;
    private Statement getProductCount, getAllProducts;
    private ResourceBundle resourceBundle;
    private ResultSet resultSet;

    public ProductDaoImpl() {
        conn = PostgresConnectionHelper.getConnection();
        resourceBundle = ResourceBundle.getBundle("db");
    }

    @Override
    public int getProductCount() {
        String getProductCountSql = resourceBundle.getString("getproductcount");
        try {
            getProductCount = conn.createStatement();
            resultSet = getProductCount.executeQuery(getProductCountSql);
            resultSet.next();
            return resultSet.getInt("count");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return 0;
        }
    }

    @Override
    public void addProductToDB(Product product) {
        String addProductSql = resourceBundle.getString("addproduct");
        try {
            addProductPre = conn.prepareStatement(addProductSql);
            addProductPre.setString(1, product.getProductName());
            addProductPre.setString(2, product.getProductDescription());
            addProductPre.setDate(3, Date.valueOf(LocalDate.now()));
            addProductPre.setString(4, product.getImgUrl());
            addProductPre.setInt(5, product.getQuantity());
            addProductPre.setFloat(6, product.getPrice());
            addProductPre.executeUpdate();
            conn.commit();
        } catch (Exception e) {
            System.out.println(e + "HERE");
        }
    }

    @Override
    public Product getProductById(int id) {
        Product receive = new Product();
        String getProductByIdSql = resourceBundle.getString("getproductbyid");
        try {
            getProductByIdPre = conn.prepareStatement(getProductByIdSql);
            getProductByIdPre.setInt(1, id);
            resultSet = getProductByIdPre.executeQuery();
            if (!resultSet.next())
                return null;
            receive.setPid(resultSet.getInt("id"));
            receive.setProductName(resultSet.getString("name"));
            receive.setProductDescription(resultSet.getString("description"));
            receive.setImgUrl(resultSet.getString("image_url"));
            receive.setDate(resultSet.getDate("last_update").toLocalDate());
            receive.setQuantity(resultSet.getInt("quantity"));
            receive.setPrice(resultSet.getFloat("price"));
            return receive;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    @Override
    public Product getProductByName(String name) {
        Product receive = new Product();
        String getProductByNameSql = resourceBundle.getString("getproductbyname");
        try {
            getProductByNamePre = conn.prepareStatement(getProductByNameSql);
            getProductByNamePre.setString(1, name);
            resultSet = getProductByNamePre.executeQuery();
            if (!resultSet.next())
                return null;
            receive.setPid(resultSet.getInt("id"));
            receive.setProductName(resultSet.getString("name"));
            receive.setProductDescription(resultSet.getString("description"));
            receive.setImgUrl(resultSet.getString("image_url"));
            receive.setDate(resultSet.getDate("last_update").toLocalDate());
            receive.setQuantity(resultSet.getInt("quantity"));
            receive.setPrice(resultSet.getFloat("price"));
            return receive;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            ;
            return null;
        }
    }

    @Override
    public boolean removeProductById(int id) {
        String removeProductByIdSql = resourceBundle.getString("removeproductbyid");
        try {
            removeProductByIdPre = conn.prepareStatement(removeProductByIdSql);
            removeProductByIdPre.setInt(1, id);
            int result = removeProductByIdPre.executeUpdate();
            conn.commit();
            if (result == 0) {
                System.out.println("Product doesn't exists");
                return false;
            } else {
                System.out.println("Product deleted");
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    @Override
    public void modifyProductById(Product product) {
        String updateProductSql = resourceBundle.getString("modifyproduct");
        try {
            modifyProductPre = conn.prepareStatement(updateProductSql);
            modifyProductPre.setString(1, product.getProductName());
            modifyProductPre.setString(2, product.getProductDescription());
            modifyProductPre.setDate(3, Date.valueOf(product.getDate()));
            modifyProductPre.setString(4, product.getImgUrl());
            modifyProductPre.setInt(5, product.getQuantity());
            modifyProductPre.setFloat(6, product.getPrice());
            modifyProductPre.setInt(7, product.getPid());
            modifyProductPre.executeUpdate();
            conn.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<Product>();
        String getAllProductSql = resourceBundle.getString("getallproducts");
        try {
            getAllProducts = conn.createStatement();
            resultSet = getAllProducts.executeQuery(getAllProductSql);
            if (!resultSet.next())
                return null;
            do {
                Product receive = new Product();
                receive.setPid(resultSet.getInt("id"));
                receive.setProductName(resultSet.getString("name"));
                receive.setProductDescription(resultSet.getString("description"));
                receive.setImgUrl(resultSet.getString("image_url"));
                receive.setDate(resultSet.getDate("last_update").toLocalDate());
                receive.setQuantity(resultSet.getInt("quantity"));
                receive.setPrice(resultSet.getFloat("price"));
                productList.add(receive);
            } while (resultSet.next());
            return productList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }
}