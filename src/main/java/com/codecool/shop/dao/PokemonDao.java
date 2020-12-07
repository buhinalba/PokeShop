package com.codecool.shop.dao;

import com.codecool.shop.model.Pokemon;
import com.codecool.shop.model.Supplier;
import com.codecool.shop.model.ProductCategory;

import java.util.List;

public interface PokemonDao {

    void add(Pokemon pokemon);
    Pokemon find(int id);
    void remove(int id);

    List<Pokemon> getAll();
    List<Pokemon> getBy(ProductCategory productCategory);

}
