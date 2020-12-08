package com.codecool.shop.dao;

import com.codecool.shop.model.Pokemon;

import java.io.IOException;
import java.util.List;

public interface PokemonGetAllDaoInt extends UtilDao {

    List<Pokemon> getAll() throws IOException;

}
