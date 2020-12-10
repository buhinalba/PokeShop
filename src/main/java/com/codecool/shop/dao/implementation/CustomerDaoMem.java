package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.CustomerDao;
import com.codecool.shop.dao.PokemonDao;
import com.codecool.shop.dao.UtilDao;
import com.codecool.shop.model.Customer;
import com.codecool.shop.model.Pokemon;
import com.codecool.shop.model.PokemonCategory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class CustomerDaoMem implements CustomerDao {

    private List<Customer> data = new ArrayList<>();
    private static CustomerDaoMem instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private CustomerDaoMem() {
    }

    public void setData(List<Customer> customers) {
        data = customers;
    }


    public static CustomerDaoMem getInstance() {
        if (instance == null) {
            instance = new CustomerDaoMem();
        }
        return instance;
    }

    @Override
    public void add(Customer customer) {
        customer.setId(data.size() + 1);
        data.add(customer);
    }

    @Override
    public Customer find(int id) {
        return data.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        data.remove(find(id));
    }

    @Override
    public List<Customer> getAll() {
        return data;
    }

    public void clearMem() {
        data.clear();
    }

}