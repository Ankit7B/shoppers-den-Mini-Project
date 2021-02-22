package com.tata.shoppersden.dao;

import com.tata.shoppersden.models.Product;

public interface AdminDao {
    public <E> void addUser(E user);
    public void removeUser(int id);
    public <E> void modifyUser(E user);
    public <E> E getUser(int id);
    public void viewUsersList();
    public int getCount();
    public boolean checkIfUserExists(String email);
}
