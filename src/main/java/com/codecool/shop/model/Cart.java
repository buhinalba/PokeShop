package com.codecool.shop.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Cart {
    private String userName;
    private int orderId;
    private ArrayList<Pokemon> pokemons;

    public Cart() {
       pokemons = new ArrayList<>();
       orderId = new Random().nextInt(10000 - 1000) + 1000;
    }

    private String getName() {
        return userName;
    }

    private void setName(String name) {
        this.userName = name;
    }

    public int getId() {
        return orderId;
    }

    public void setId(int id) {
        this.orderId = orderId;
    }

    public ArrayList<Pokemon> getPokemons() {
        return pokemons;
    }

    public void setPokemons(ArrayList<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }

    public void addPokemonToCart(Pokemon pokemon) {
        this.pokemons.add(pokemon);
    }

    public void removePokemonFromCart(Pokemon pokemon) {
        pokemons.remove(pokemon);
    }

    public void removeAllCurrentPokemonFromCart(Pokemon pokemon) {
        int numOfPokemonInCart = howManyOfOne(pokemon);
        for (int i = 0; i < numOfPokemonInCart; i++) {
            removePokemonFromCart(pokemon);
        }
    }

    public void removeAllPokemonFromCart() {
        pokemons = new ArrayList<>();
    }

    public int howManyOfAll() {
        return this.pokemons.size();
    }

    public int howManyOfOne(Pokemon pokemon) {
        if ( pokemons.size() > 0 ) {
            int count = Collections.frequency(pokemons, pokemon);
            return count;
        }
        return 0;
    }

    @Override
    public String toString() {
        return null;
    }
}
