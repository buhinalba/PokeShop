package com.codecool.shop.model;

import java.util.List;
import java.util.stream.Collectors;

public class Cart {
    private List<Pokemon> pokemons;
    private int id;

    public Cart() {}
    public Cart(Pokemon pokemon) { addPokemonToCart(pokemon); }
    public Cart(List<Pokemon> pokemons) { setPokemons(pokemons); }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Pokemon> getPokemons() {
        return pokemons;
    }

    public void setPokemons(List<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }

    public void addPokemonToCart(Pokemon pokemon){
        this.pokemons.add(pokemon);
    }

    public void removePokemon(int id){
        List<Pokemon> filtered = pokemons.stream()
                .filter(pokemon -> pokemon.getId() == id)
                .collect(Collectors.toList());
        setPokemons(filtered);
    }

    public void removePokemon(String name){
        List<Pokemon> filtered = pokemons.stream()
                .filter(pokemon -> pokemon.getName().equals(name))
                .collect(Collectors.toList());
        setPokemons(filtered);
    }
}
