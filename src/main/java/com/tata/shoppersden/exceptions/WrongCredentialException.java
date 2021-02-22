package com.tata.shoppersden.exceptions;

public class WrongCredentialException extends Exception {
    public WrongCredentialException(String err) {
        super(err);
    }
}
