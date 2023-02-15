package com.kameleoon.quotes.model;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private String userId;
    private String name;
    private String email;
    private String password;
    private Date dateRegistration;
}
