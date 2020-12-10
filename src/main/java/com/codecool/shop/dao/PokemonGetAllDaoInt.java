package com.codecool.shop.dao;

import com.codecool.shop.model.Pokemon;

import java.io.IOException;
import java.util.List;

public interface PokemonGetAllDaoInt extends UtilDao {

    List<Pokemon> getAll(String urlString) throws IOException;

    void addAllPokemonsToPokemonDaoMem(String urlString) throws IOException;

    String pokemonPagination(int offset) throws IOException;

}
