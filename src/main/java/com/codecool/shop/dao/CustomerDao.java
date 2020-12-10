package com.codecool.shop.dao;

import com.codecool.shop.model.Customer;

import java.util.List;

public interface CustomerDao {

    void add(Customer customer);
    Customer find(int id);
    void remove(int id);

    List<Customer> getAll();
}
