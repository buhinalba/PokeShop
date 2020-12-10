package com.codecool.shop.dao;

import com.codecool.shop.model.Cart;

import com.codecool.shop.model.Pokemon;
import com.codecool.shop.model.PokemonCategory;

import java.util.HashMap;
import java.util.List;

public interface CartDao {
    void add(Pokemon pokemon);

    Pokemon findPokemon(int id);

    void decreasePokemon(int id);

    HashMap<Pokemon, Integer> getAll();
}
