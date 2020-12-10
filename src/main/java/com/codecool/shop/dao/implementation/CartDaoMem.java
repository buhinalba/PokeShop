package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Pokemon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartDaoMem implements CartDao {

    private Cart cart = new Cart();
    private static CartDaoMem instance = null;

    private CartDaoMem() {
    }

    public static CartDaoMem getInstance() {
        if (instance == null) {
            instance = new CartDaoMem();
        }
        return instance;
    }

    @Override
    public void add(Pokemon pokemon) {
        cart.addPokemonToCart(pokemon);
    }

    @Override
    public Pokemon findPokemon(int id) {
        return null; //cart.getPokemons().stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void removePokemon(int id) {
        cart.removePokemon(id);
    }

    @Override
    public HashMap<Pokemon, Integer> getAll() {
        return cart.getPokemons();
    }

    public int getTotalPrice() {
        int sum = 0;
        for (Map.Entry<Pokemon, Integer> entry : this.getAll().entrySet()) {
            sum += entry.getKey().getDefaultPrice() * entry.getValue();
        }
        return sum;
    }

    public int getTotalAmount() {
        int sum = 0;
        for (Map.Entry<Pokemon, Integer> entry : this.getAll().entrySet()) {
            sum += entry.getValue();
        }
        return sum;
    }

}
