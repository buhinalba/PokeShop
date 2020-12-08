package com.codecool.shop.model;

import java.util.ArrayList;
import java.util.List;

public class PokemonCategory extends BaseModel {
    private List<Pokemon> pokemons;

    public PokemonCategory(String name) {
        super(name);
        this.pokemons = new ArrayList<>();
    }

    public String getCategory() {
        return getName();
    }

    public void setCategory(String category) {
        this.name = category;
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
                    "category: %2$s, ",
                this.id,
                this.name);
    }
}