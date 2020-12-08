package com.codecool.shop.dao;

import com.codecool.shop.model.Pokemon;

import java.util.List;

public interface PokemonFilterDao extends UtilDao {
    List<Pokemon> getPokemons();
}
