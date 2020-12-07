package com.codecool.shop.dao.implementation;


import com.codecool.shop.dao.PokemonCategoryDao;
import com.codecool.shop.model.ProductCategory;

import java.util.ArrayList;
import java.util.List;

public class PokemonCategoryDaoMem implements PokemonCategoryDao {

    private List<ProductCategory> data = new ArrayList<>();
    private static PokemonCategoryDaoMem instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private PokemonCategoryDaoMem() {
    }

    public static PokemonCategoryDaoMem getInstance() {
        if (instance == null) {
            instance = new PokemonCategoryDaoMem();
        }
        return instance;
    }

    @Override
    public void add(ProductCategory category) {
        category.setId(data.size() + 1);
        data.add(category);
    }

    @Override
    public ProductCategory find(int id) {
        return data.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        data.remove(find(id));
    }

    @Override
    public List<ProductCategory> getAll() {
        return data;
    }
}
