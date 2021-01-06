package com.codecool.shop.model;

public class User extends Customer {
    private final String password;

    public User(String name, String email, String password) {
        super(name);
        setEmail(email);
        // todo save hash version of password
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
