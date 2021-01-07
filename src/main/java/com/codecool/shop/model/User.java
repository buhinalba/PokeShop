package com.codecool.shop.model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

public class User extends Customer {
    private final String password;
    private static final String HASH_ALGORITHM = "SHA-512";

    public User(String name, String email, String password) {
        super(name);
        setEmail(email);
        this.password = hashPassword(password);
    }

    public User(String name, String email, String password, boolean isHashed) {
        super(name);
        if (isHashed) {
            this.password = password;
        } else {
            this.password = hashPassword(password);
        }
        setEmail(email);
    }

    public String getPassword() {
        return password;
    }

    public static String hashPassword(String password) throws RuntimeException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[0];       // to make the login authentication a lot easier :)
        random.nextBytes(salt);
        try {
            MessageDigest md = MessageDigest.getInstance(HASH_ALGORITHM);
            md.update(salt);
            byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
            return Arrays.toString(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Algorithm not existent: " +HASH_ALGORITHM);
        }
    }
}
