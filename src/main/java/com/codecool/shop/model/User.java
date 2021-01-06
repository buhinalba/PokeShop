package com.codecool.shop.model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

public class User extends Customer {
    private final String password;
    private final String HASH_ALGORITHM = "SHA-512";

    public User(String name, String email, String password) {
        super(name);
        setEmail(email);
        this.password = hashPassword(password);
        System.out.println(this.password + " " + this.password.length());
    }

    public String getPassword() {
        return password;
    }

    private String hashPassword(String password) throws RuntimeException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[0];       // to make the login authentication a lot easier :)
        random.nextBytes(salt);
        try {
             MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
            return Arrays.toString(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Algorithm not existent: " +HASH_ALGORITHM);
        }
    }
}
