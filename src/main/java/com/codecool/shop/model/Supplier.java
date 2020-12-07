package com.codecool.shop.model;

import java.util.ArrayList;
import java.util.List;

public class Supplier extends BaseModel {
    private List<Pokemon> pokemons;

    public Supplier(String name, String description) {
        super(name);
        this.pokemons = new ArrayList<>();
    }

    public void setProducts(ArrayList<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }

    public List<Pokemon> getProducts() {
        return this.pokemons;
    }

    public void addProduct(Pokemon pokemon) {
        this.pokemons.add(pokemon);
    }

    @Override
    public String toString() {
        return String.format("id: %1$d, " +
                        "name: %2$s, " +
                        "description: %3$s",
                this.id,
                this.name,
                this.description
        );
    }
}