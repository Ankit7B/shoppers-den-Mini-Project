package com.tata.shoppersden.ui.tests;

import com.tata.shoppersden.dao.LoginDaoImpl;
import com.tata.shoppersden.helpers.PostgresConnectionHelper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {
    private static Connection conn;
    private static ResourceBundle resourceBundle;
    private static LoginDaoImpl loginDaoImpl;
    private Object user;

    @BeforeAll
    static void initial() {
        conn = PostgresConnectionHelper.getConnection();
        resourceBundle = ResourceBundle.getBundle("db");
        loginDaoImpl = new LoginDaoImpl();
    }

    @Test
    public void customerLoginTest() {
        user = loginDaoImpl.login("abc@gmail.com", "abcpassword");
        System.out.println(user);
        assertNotNull(user);
    }

    @Test
    public void negativeLoginTest() {
        user = loginDaoImpl.login("abc@gmail.com", "pass");
        assertNull(user);
    }
}
