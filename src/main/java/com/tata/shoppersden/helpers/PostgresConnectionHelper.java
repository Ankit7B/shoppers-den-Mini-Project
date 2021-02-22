package com.tata.shoppersden.helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PostgresConnectionHelper {
    private static ResourceBundle resourceBundle;
    private static Connection conn;
    public static Connection getConnection() {
        resourceBundle = ResourceBundle.getBundle("db");
        String userName = resourceBundle.getString("username");
        String password = resourceBundle.getString("password");
        String url = resourceBundle.getString("url");

        try {
            conn = DriverManager.getConnection(url, userName, password);
            conn.setAutoCommit(false);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return conn;
    }

}
