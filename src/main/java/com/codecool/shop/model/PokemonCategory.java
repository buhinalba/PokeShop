package com.codecool.shop.model;

import java.util.ArrayList;
import java.util.List;

public class PokemonCategory extends BaseModel {
    private String category;
    private List<Pokemon> pokemons;

    public PokemonCategory(String name, String category) {
        super(name);
        this.category = category;
        this.pokemons = new ArrayList<>();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPokemons(ArrayList<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }

    public List<Pokemon> getPokemons() {
        return this.pokemons;
    }

    public void addPokemon(Pokemon pokemon) {
        this.pokemons.add(pokemon);
    }

    @Override
    public String toString() {
        return String.format(
                "id: %1$d," +
                        "name: %2$s, " +
                        "category: %3$s, " +
                        "description: %4$s",
                this.id,
                this.name,
                this.category);
    }
}