package com.codecool.shop.dao;

import com.codecool.shop.model.Pokemon;

import java.util.List;

public interface PokemonFilter extends UtilDao {
    List<Pokemon> getPokemons();
}
