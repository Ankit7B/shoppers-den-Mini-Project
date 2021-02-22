package com.tata.shoppersden.dao;

import com.tata.shoppersden.helpers.PostgresConnectionHelper;
import com.tata.shoppersden.models.Product;
import com.tata.shoppersden.models.ShoppingCart;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class ShoppingCartDaoImpl implements ShoppingCartDao {
    private ShoppingCart cart;
    private PreparedStatement putCartToDbPre, getCartIdPre, pre;
    private ResourceBundle resourceBundle;
    private Connection conn;
    private ResultSet resultSet;

    public ShoppingCartDaoImpl() {
        cart = new ShoppingCart();
        resourceBundle = ResourceBundle.getBundle("db");
        conn = PostgresConnectionHelper.getConnection();
        initialSave();
    }


    // Deserialization of data to make shoppingcart object.
    public ShoppingCartDaoImpl(int cartId) {
        resourceBundle = ResourceBundle.getBundle("db");
        String getCartById = resourceBundle.getString("getcartbyid");
        resourceBundle = ResourceBundle.getBundle("db");
        conn = PostgresConnectionHelper.getConnection();
        try {
            pre = conn.prepareStatement(getCartById);
            pre.setInt(1, cartId);
            resultSet = pre.executeQuery();
            if(resultSet.next()) {
                byte[] data = resultSet.getBytes("shoppingcart");
                ByteArrayInputStream bis = new ByteArrayInputStream(data);
                ObjectInput in = null;
                in = new ObjectInputStream(bis);
                Object obj = in.readObject();
                cart = (ShoppingCart) obj;
                cart.setCartId(cartId);
                System.out.println("cart loaded");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    @Override
    public void addProductToCart(Product product) {
        try {
            cart.addProduct(product);
            cart.setPrice(cart.getPrice() + product.getQuantity() * product.getPrice());
            System.out.println(cart);
            this.saveState();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    @Override
    public void removeProductFromCart(int id) {
        try {
            float red = 0.00f;
            for(Product product: cart.getProductList()) {
                if (product.getPid() == id) {
                    red = product.getPrice() * product.getQuantity();
                }
            }
            cart.removeProduct(id);
            cart.setPrice(cart.getPrice() - red);
            this.saveState();
        } catch (Exception e) {
            System.out.println(e);
        }
    }



    @Override
    public void viewAllProducts() {
        System.out.println(cart);
    }

    // Serialization and initial save of cart to db.
    void initialSave() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(byteArrayOutputStream);
            out.writeObject(cart);
            out.flush();
            byte[] dataBytes = byteArrayOutputStream.toByteArray();
            String putCartToDbSql = resourceBundle.getString("addshoppingcart");
            putCartToDbPre = conn.prepareStatement(putCartToDbSql);
            putCartToDbPre.setBytes(1, dataBytes);
            putCartToDbPre.executeUpdate();
            conn.commit();
            String getShoppingCartId = resourceBundle.getString("getcartid");
            getCartIdPre = conn.prepareStatement(getShoppingCartId);
            getCartIdPre.setBytes(1, dataBytes);
            resultSet = getCartIdPre.executeQuery();
            if(!resultSet.next())
                System.out.println("empty result set");
            int id = resultSet.getInt("id");
            cart.setCartId(id);
            System.out.println("cart ID - " + id);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // Serialization and saving the cart to DB
    void saveState() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(byteArrayOutputStream);
            out.writeObject(cart);
            out.flush();
            byte[] dataBytes = byteArrayOutputStream.toByteArray();
            String putCartToDbSql = resourceBundle.getString("updateshoppingcart");
            putCartToDbPre = conn.prepareStatement(putCartToDbSql);
            putCartToDbPre.setBytes(1, dataBytes);
            putCartToDbPre.setInt(2, cart.getCartId());
            putCartToDbPre.executeUpdate();
            conn.commit();
            System.out.println("Cart Saved");
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    @Override
    public void pay() {

        TransactionDao transactionDaoImpl = new TransactionDaoImpl();
        transactionDaoImpl.pay(cart);

//        Transaction transaction = new Transaction();
//        transaction.setCartId(cart.getCartId());
//        ProductDao productDaoImpl = new ProductDaoImpl();
//        for(Product product: cart.getProductList()) {
//            int initial = productDaoImpl.getProductById(product.getPid()).getQuantity();
//            int result = initial - product.getQuantity();
//            Product modifiedProduct = productDaoImpl.getProductById(product.getPid());
//            modifiedProduct.setQuantity(result);
//            productDaoImpl.modifyProductById(modifiedProduct);
//        }
//        System.out.println("Product DB Updated");
//        transaction.setTotalPrice(cart.getPrice());
//        String addTransactionSql = resourceBundle.getString("addtransaction");
//        try {
//            PreparedStatement addTransactionPre = conn.prepareStatement(addTransactionSql);
//            addTransactionPre.setInt(1, cart.getCartId());
//            addTransactionPre.setFloat(2, cart.getPrice());
//            addTransactionPre.executeUpdate();
//            conn.commit();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        System.out.println("Total Price = " + cart.getPrice());
//        System.out.println("Paid");
    }

    @Override
    public int getCartId() {
        return cart.getCartId();
    }
}
