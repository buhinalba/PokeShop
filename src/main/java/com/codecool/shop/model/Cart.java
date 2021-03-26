package com.codecool.shop.model;

import com.codecool.shop.controller.OrderLog;

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

    public void addPokemonToCart(int pokemonId) {
        for (Map.Entry<Pokemon, Integer> entry : pokemons.entrySet()) {
            if ((entry.getKey()).getId() == pokemonId) {
                entry.setValue(entry.getValue() + 1);
                return;
            }
        }
    }

    @Override
    public String toString() {
        return String.format("id: %1$d, " +
                        "userName: %2$s, ",
                this.id,
                this.userName);
    }

    public void decreasePokemonCount(int id) {
        for (Map.Entry<Pokemon, Integer> entry : pokemons.entrySet()) {
            if ((entry.getKey()).getId() == id) {
                if (entry.getValue().equals(1)) {
                    return;
                } else {
                    entry.setValue(entry.getValue() - 1);
                }
            }
        }

    }

    public void deletePokemon(int id) {
        pokemons.entrySet().removeIf(entry -> (entry.getKey()).getId() == id);
    }

    public int getPokemonCount(int pokemonId) {
        for(Map.Entry<Pokemon, Integer> entry: pokemons.entrySet()) {
            if (entry.getKey().getId() == pokemonId) {
                return entry.getValue();
            }
        }
        return 0;
    }

    public Pokemon findPokemon(int id) {
        for(Map.Entry<Pokemon, Integer> entry: pokemons.entrySet()) {
            if (entry.getKey().getId() == id) {
                return entry.getKey();
            }
        }
        throw new NoSuchElementException("Pokemon with id " + id + "  not found in cart!");
    }
}
