package com.codecool.shop.business.logic;

import com.codecool.shop.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class SessionController {
    private List<Customer> activeCustomers = new ArrayList<>();

    public void addSession(Customer customer) {
        activeCustomers.add(customer);
    }

    public void isLoggedIn(String JWToken) {

    }
}
