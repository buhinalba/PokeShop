package com.codecool.shop.dao.jdbc;

import com.codecool.shop.dao.UserDao;
import com.codecool.shop.model.Customer;
import com.codecool.shop.model.User;

import java.util.List;

public class UserDaoJdbc implements UserDao {

    @Override
    public void add(Customer customer) {
        // todo save into db
    }

    @Override
    public User find(String email) {
        return null;
    }

    @Override
    public boolean emailExists(String email) {
        return false;
    }

    @Override
    public Customer find(int id) {
        return null;
    }

    @Override
    public void remove(int id) {
        // delete account feature for the future
    }

    @Override
    public List<Customer> getAll() {
        return null;
    }
}
