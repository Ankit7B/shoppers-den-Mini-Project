package com.tata.shoppersden.dao;

import com.tata.shoppersden.exceptions.UserAlreadyExists;
import com.tata.shoppersden.helpers.PostgresConnectionHelper;
import com.tata.shoppersden.models.Admin;
import com.tata.shoppersden.models.Customer;
import com.tata.shoppersden.models.Product;

import java.sql.*;
import java.util.ResourceBundle;

public class AdminDaoImpl implements AdminDao {
    private Connection conn;
    private ResourceBundle resourceBundle;
    private PreparedStatement addUserPre, removeUserPre, modifyUserPre, getUserPre, getUserByEmailPre, getAllUsersPre, addAdminPre, pre;
    private Statement getUserStatement, countUsersStatement;
    private ResultSet resultset;

    public AdminDaoImpl() {
        conn = PostgresConnectionHelper.getConnection();
        if (conn != null) {
            System.out.println("Connection Ready");
        } else {
            System.out.println("Connection Not Ready");
        }
        resourceBundle = ResourceBundle.getBundle("db");
    }

    public boolean checkIfUserExists(String email) {
        String getUserByEmailSql = resourceBundle.getString("getuserbyemail");
        try {
            getUserByEmailPre = conn.prepareStatement(getUserByEmailSql);
            getUserByEmailPre.setString(1, email);
            resultset = getUserByEmailPre.executeQuery();
            if(!resultset.next()) {
                return false;
            } else {
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }


    void checkUserIfAlreadyExists(String email) throws UserAlreadyExists {
        if (checkIfUserExists(email)) {
            throw new UserAlreadyExists("User Already Exists");
        }
    }

    boolean checkIfAdminExists(String username) {
        String getUserByUsernameSql = resourceBundle.getString("getadminbyusername");
        try {
            getUserByEmailPre = conn.prepareStatement(getUserByUsernameSql);
            getUserByEmailPre.setString(1, username);
            resultset = getUserByEmailPre.executeQuery();
            if(!resultset.next()) {
                return false;
            } else {
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    void checkAdminIfAlreadyExists(String username) throws UserAlreadyExists {
        if (checkIfAdminExists(username)) {
            throw new UserAlreadyExists("Admin Already Exists");
        }
    }

    @Override
    public <E> void addUser(E user) {
        String addUserSql = resourceBundle.getString("adduser");
        String addAdminSql = resourceBundle.getString("addadmin");
        try {
            addUserPre = conn.prepareStatement(addUserSql);
            if (user instanceof Customer) {
                Customer temp = (Customer) user;
                ShoppingCartDao shoppingCartDaoImpl = new ShoppingCartDaoImpl();
                int cart_id = shoppingCartDaoImpl.getCartId();
                checkUserIfAlreadyExists(temp.getEmail());
                addUserPre.setString(1, temp.getName());
                addUserPre.setString(2, temp.getEmail());
                addUserPre.setString(3, temp.getPassword());
                addUserPre.setString(4, temp.getPhoneNumber());
                addUserPre.setString(5, temp.getSecretQuestion());
                addUserPre.setString(6, temp.getSecretAnswer());
                addUserPre.setString(7, temp.getAddress());
                addUserPre.setInt(8, cart_id);
            } else {
                Admin temp = (Admin) user;
                checkAdminIfAlreadyExists(temp.getAdminName());
                addAdminPre = conn.prepareStatement(addAdminSql);
                addAdminPre.setString(1, temp.getAdminName());
                addAdminPre.setString(2, temp.getAdminPassword());
            }
            addUserPre.executeUpdate();
            conn.commit();
        } catch (SQLException | UserAlreadyExists throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void removeUser(int id) {
        String removeUserSql = resourceBundle.getString("removeuser");
        try {
            removeUserPre = conn.prepareStatement(removeUserSql);
            removeUserPre.setInt(1, id);
            removeUserPre.executeUpdate();
            conn.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // TODO - To Implement modify users.
    @Override
    public <E> void modifyUser(E user) {
//        int id;
//        if (user instanceof Customer) {
//            id = ((Customer) user).getUserId();
//
//        }
    }

    @Override
    public <E> E getUser(int id) {
        String getUserSql = resourceBundle.getString("getuser");
        try {
            getUserPre = conn.prepareStatement(getUserSql);
            getUserPre.setInt(1, id);
            resultset = getUserPre.executeQuery();
            resultset.next();
            Customer customer = new Customer();
            customer.setUserId(resultset.getInt("id"));
            customer.setName(resultset.getString("name"));
            customer.setEmail(resultset.getString("email"));
            customer.setAddress(resultset.getString("address"));
            customer.setPhoneNumber(resultset.getString("phone_no"));
            customer.setSecretQuestion(resultset.getString("question"));
            customer.setSecretAnswer(resultset.getString("answer"));
            customer.setPassword(resultset.getString("password"));
            return (E) customer;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    @Override
    public void viewUsersList() {
        String getAllUsersSql = resourceBundle.getString("getallusers");
        try {
            getAllUsersPre = conn.prepareStatement(getAllUsersSql);
            resultset = getAllUsersPre.executeQuery();
            int ctr = 1;
            while(resultset.next()) {
                Customer user = new Customer();
                user.setUserId(resultset.getInt("id"));
                user.setName(resultset.getString("name"));
                user.setEmail(resultset.getString("email"));
                user.setAddress(resultset.getString("address"));
                user.setPhoneNumber(resultset.getString("phone_no"));
                user.setSecretQuestion(resultset.getString("question"));
                user.setSecretAnswer(resultset.getString("answer"));
                user.setPassword(resultset.getString("password"));
                System.out.println(ctr + " - " + user);
                ctr++;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public int getCount() {
        String getCountSql = resourceBundle.getString("countusers");
        try {
            countUsersStatement = conn.createStatement();
            resultset = countUsersStatement.executeQuery(getCountSql);
            resultset.next();
            return resultset.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }
}
