package com.codecool.shop.model;

import java.util.List;

public class Cart {
    private List<Pokemon> pokemons;

    public Cart() {}
    public Cart(Pokemon pokemon) { addPokemonToCart(pokemon); }
    public Cart(List<Pokemon> pokemons) { setPokemons(pokemons); }

    public List<Pokemon> getPokemons() {
        return pokemons;
    }

    public void setPokemons(List<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }

    public void addPokemonToCart(Pokemon pokemon){
        this.pokemons.add(pokemon);
    }
}
