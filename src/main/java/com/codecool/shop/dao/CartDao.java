package com.codecool.shop.dao;

import com.codecool.shop.model.Pokemon;

import java.util.HashMap;


public interface CartDao {
    void add(Pokemon pokemon);

    Pokemon findPokemon(int id);

    void decreasePokemon(int id);

    HashMap<Pokemon, Integer> getAll();
}
