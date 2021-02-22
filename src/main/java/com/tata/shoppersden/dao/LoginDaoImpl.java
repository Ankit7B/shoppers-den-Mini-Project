package com.tata.shoppersden.dao;

import com.tata.shoppersden.exceptions.WrongCredentialException;
import com.tata.shoppersden.helpers.PostgresConnectionHelper;
import com.tata.shoppersden.models.Admin;
import com.tata.shoppersden.models.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class LoginDaoImpl implements LoginDao {
    private ResourceBundle resourceBundle;
    private Connection conn;
    private PreparedStatement pre;
    private ResultSet resultSet;

    public LoginDaoImpl() {
        resourceBundle = ResourceBundle.getBundle("db");
        conn = PostgresConnectionHelper.getConnection();
    }

    public void wrongCredentials() throws WrongCredentialException {
        throw new WrongCredentialException("Wrong email/password");
    }

    @Override
    public <E> E login(String email, String password) {
        String loginUserSql = resourceBundle.getString("loginuser");
        String loginAdminSql = resourceBundle.getString("loginadmin");
        try {
            pre = conn.prepareStatement(loginUserSql);
            pre.setString(1, email);
            resultSet = pre.executeQuery();
            if(resultSet.next()) {
                if (resultSet.getString("password").equals(password)) {
                    Customer user = new Customer();
                    user.setName(resultSet.getString("name"));
                    user.setUserId(resultSet.getInt("id"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPassword(resultSet.getString("password"));
                    user.setAddress(resultSet.getString("address"));
                    user.setPhoneNumber(resultSet.getString("phone_no"));
                    user.setSecretQuestion(resultSet.getString("question"));
                    user.setSecretAnswer(resultSet.getString("answer"));
                    user.setUserId(resultSet.getInt("id"));
                    user.setCartId(resultSet.getInt("cart_id"));
                    return (E) user;
                } else {
                    wrongCredentials();
                    return null;
                }
            } else {
                pre = conn.prepareStatement(loginAdminSql);
                pre.setString(1, email);
                resultSet = pre.executeQuery();
                if(resultSet.next()) {
                    Admin user = new Admin();
                    if (resultSet.getString("admin_password").equals(password)) {
                        user.setAdminName(resultSet.getString("admin_name"));
                        user.setAdminPassword(resultSet.getString("admin_password"));
                        user.setAdminId(resultSet.getInt("admin_id"));
                        return (E) user;
                    } else {
                        wrongCredentials();
                        return null;
                    }
                }
            }
            wrongCredentials();
            return null;
        } catch (Exception e) {
            System.out.println(e + "login wala");
            return null;
        }
    }
}
