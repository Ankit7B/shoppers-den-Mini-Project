package com.tata.shoppersden.dao;

import com.tata.shoppersden.helpers.PostgresConnectionHelper;
import com.tata.shoppersden.models.Product;
import com.tata.shoppersden.models.ShoppingCart;
import com.tata.shoppersden.models.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TransactionDaoImpl implements TransactionDao {
    private Connection conn;
    private ResourceBundle resourceBundle;
    public TransactionDaoImpl() {
        conn = PostgresConnectionHelper.getConnection();
        resourceBundle = ResourceBundle.getBundle("db");
    }

    @Override
    public void pay(ShoppingCart cart) {
        Transaction transaction = new Transaction();
        transaction.setCartId(cart.getCartId());
        ProductDao productDaoImpl = new ProductDaoImpl();
        for(Product product: cart.getProductList()) {
            int initial = productDaoImpl.getProductById(product.getPid()).getQuantity();
            int result = initial - product.getQuantity();
            Product modifiedProduct = productDaoImpl.getProductById(product.getPid());
            modifiedProduct.setQuantity(result);
            productDaoImpl.modifyProductById(modifiedProduct);
        }
        System.out.println("Product DB Updated");
        transaction.setTotalPrice(cart.getPrice());
        String addTransactionSql = resourceBundle.getString("addtransaction");
        try {
            PreparedStatement addTransactionPre = conn.prepareStatement(addTransactionSql);
            addTransactionPre.setInt(1, cart.getCartId());
            addTransactionPre.setFloat(2, cart.getPrice());
            addTransactionPre.executeUpdate();
            conn.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("Total Price = " + cart.getPrice());
        System.out.println("Paid");

    }
}
