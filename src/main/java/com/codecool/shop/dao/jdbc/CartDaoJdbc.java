package com.codecool.shop.dao.jdbc;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.model.Pokemon;

import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;

public class CartDaoJdbc implements CartDao {
    private DataSource dataSource;

    public CartDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Pokemon pokemon) {
    }

    @Override
    public Pokemon findPokemon(int id) {
        return null;
    }

    @Override
    public void decreasePokemon(int id) {

    }

    @Override
    public HashMap<Pokemon, Integer> getAll() {
        return null;
    }
}
