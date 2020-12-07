package com.codecool.shop.dao.implementation;


import com.codecool.shop.dao.PokemonDao;
import com.codecool.shop.model.Pokemon;
import com.codecool.shop.model.PokemonCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PokemonDaoMem implements PokemonDao {

    private List<Pokemon> data = new ArrayList<>();
    private static PokemonDaoMem instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private PokemonDaoMem() {
    }

    public static PokemonDaoMem getInstance() {
        if (instance == null) {
            instance = new PokemonDaoMem();
        }
        return instance;
    }

    public void addAll(List<Pokemon> pokemonList) {
        for (Pokemon pokemon : pokemonList) {
            add(pokemon);
        }
    }

    @Override
    public void add(Pokemon pokemon) {
        pokemon.setId(data.size() + 1);
        data.add(pokemon);
    }

    @Override
    public Pokemon find(int id) {
        return data.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        data.remove(find(id));
    }

    @Override
    public List<Pokemon> getAll() {
        return data;
    }

    @Override
    public List<Pokemon> getBy(PokemonCategory pokemonCategory) {
        return data.stream().filter(t -> t.getProductCategory().equals(pokemonCategory)).collect(Collectors.toList());
    }
}
