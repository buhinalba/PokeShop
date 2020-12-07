package com.codecool.shop.model;

import java.util.ArrayList;
import java.util.List;

public class PokemonCategory extends BaseModel {
    private String department;
    private List<Pokemon> pokemons;

    public PokemonCategory(String name, String department, String description) {
        super(name);
        this.department = department;
        this.pokemons = new ArrayList<>();
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
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
        return String.format(
                "id: %1$d," +
                        "name: %2$s, " +
                        "department: %3$s, " +
                        "description: %4$s",
                this.id,
                this.name,
                this.department,
                this.description);
    }
}