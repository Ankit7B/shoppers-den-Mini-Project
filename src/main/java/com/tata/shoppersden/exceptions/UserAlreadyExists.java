package com.tata.shoppersden.exceptions;

public class UserAlreadyExists extends Exception {
    public UserAlreadyExists(String err) {
        super(err);
    }
}
