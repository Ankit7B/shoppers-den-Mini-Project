package com.tata.shoppersden.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    private String address;
    private String email;
    private String name;
    private String phoneNumber;
    private String password;
    private String secretAnswer;
    private String secretQuestion;
    private int userId;
    private int cartId;
}
