package com.codecool.shop.dao;

import com.codecool.shop.model.User;

public interface UserDao extends CustomerDao {
    User find(String email);                 // used in authentication
    boolean emailExists(String email);      // used in registration
}
