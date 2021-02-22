package com.tata.shoppersden.dao;

public interface LoginDao {
    public <E> E login(String email, String password);
}
