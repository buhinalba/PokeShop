package com.codecool.shop.dao;

import com.codecool.shop.model.PokemonCategory;

import java.io.IOException;
import java.util.List;

public interface PokemonCategoryDao {

    void add(PokemonCategory category);
    PokemonCategory find(int id);
    void remove(int id);

    List<PokemonCategory> getAll();
    List<String> getFullCategoryNameList() throws IOException;

}
