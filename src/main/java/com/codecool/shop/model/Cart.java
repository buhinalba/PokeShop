package com.codecool.shop.model;

import java.util.*;

public class Cart {
    private HashMap<Pokemon, Integer> pokemons = new HashMap<>();
    private int id;
    private String userName;

    public Cart() {
    }

    public Cart(String name) {
        setName(name);
    }

    private void setName(String name) {
        this.userName = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public HashMap<Pokemon, Integer> getPokemons() {
        return pokemons;
    }

    public void setPokemons(HashMap<Pokemon, Integer> pokemons) {
        this.pokemons = pokemons;
    }

    public void addPokemonToCart(Pokemon pokemon) {
        Iterator<Map.Entry<Pokemon, Integer>> mapIterator = pokemons.entrySet().iterator();
        while (mapIterator.hasNext()) {
            Map.Entry<Pokemon, Integer> entry = mapIterator.next();
            if ((entry.getKey()).getId() == pokemon.getId()) {
                entry.setValue(entry.getValue() + 1);
                return;
            }
        }
        pokemons.put(pokemon, 1);
    }

    @Override
    public String toString() {
        return String.format("id: %1$d, " +
                        "userName: %2$s, ",
                this.id,
                this.userName);
    }

    public void removePokemon(int id) {
        Iterator<Map.Entry<Pokemon, Integer>> mapIterator = pokemons.entrySet().iterator();
        while (mapIterator.hasNext()) {
            Map.Entry<Pokemon, Integer> entry = mapIterator.next();
            if ((entry.getKey()).getId() == id) {
                if (entry.getValue().equals(1)) {
                    mapIterator.remove();
                }
                else {
                    entry.setValue(entry.getValue() - 1);
                }
            }
        }
    }
}
