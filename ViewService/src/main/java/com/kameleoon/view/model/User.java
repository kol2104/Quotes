package com.kameleoon.view.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class User {
    private String userId;
    private String name;
    private String email;
    private String password;
    private Date dateRegistration;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
