package com.tata.shoppersden.Dao.tests;

import com.tata.shoppersden.dao.AdminDao;
import com.tata.shoppersden.dao.AdminDaoImpl;
import com.tata.shoppersden.models.Admin;
import com.tata.shoppersden.models.Customer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AdminDaoTests {
    private static AdminDao adminDaoImpl;
    private Customer user;

    @BeforeAll
    public static void setConfigs() {
        adminDaoImpl = new AdminDaoImpl();
    }

    // TODO - need to make a mock/dummy objects to fill the database.
    @BeforeEach
    public void getInstance() {
        user = new Customer();
        user.setName("abc");
        user.setEmail("abc@gmail.com");
        user.setPassword("abcpassword");
        user.setAddress("somewhere in bangalore");
        user.setPhoneNumber("8790952010");
        user.setSecretQuestion("what is my name?");
        user.setSecretAnswer("my name is ankit");
    }

    @Test
    public void testAddUser() {
        int init = adminDaoImpl.getCount();
        adminDaoImpl.addUser(user);
        int fin = adminDaoImpl.getCount();
        assertEquals(init + 1, fin);
    }

    @Test public void testRemoveUser() {
        int init = adminDaoImpl.getCount();
        adminDaoImpl.removeUser(2);
        int fin = adminDaoImpl.getCount();
        assertEquals(init - 1, fin);
    }

    @Test
    public void testGetUser() {
        Object obj = adminDaoImpl.getUser(3);
        System.out.println(obj);
        if(obj instanceof Customer)
            assertNotNull((Customer) obj);
        else
            assertNotNull((Admin) obj);
    }

    // TODO - Test modify user method.
    @Test
    public void testModifyUser() {

    }

    @Test
    public void getAllUsers() {
        adminDaoImpl.viewUsersList();
    }

    @Test
    public void negativeCheckUserIfExists() {
        assertFalse(adminDaoImpl.checkIfUserExists("abc@gmail.com"));
    }

}
