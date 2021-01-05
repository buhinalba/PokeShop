package com.codecool.shop.dao.implementation;

import com.codecool.shop.controller.OrderLog;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Pokemon;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CartDaoMem implements CartDao {

    private Cart cart = new Cart();
    private static CartDaoMem instance = null;
    OrderLog log;

    private CartDaoMem() {
        log = new OrderLog(cart.getId());
    }

    public static CartDaoMem getInstance() {
        if (instance == null) {
            instance = new CartDaoMem();
        }
        return instance;
    }

    @Override
    public void add(Pokemon pokemon) {
        cart.addPokemonToCart(pokemon);
        log.writeLog("added", pokemon);
    }

    @Override
    public Pokemon findPokemon(int id) {
        return cart.findPokemon(id);
    }

    public void increasePokemonCount(int pokemonId) {
        cart.addPokemonToCart(pokemonId);
        log.writeLog("Increased pokemon count", findPokemon(pokemonId));
    }

    @Override
    public void decreasePokemon(int id) {
        cart.decreasePokemonCount(id);
        log.writeLog("Decreased pokemon count", findPokemon(id));
    }

    public void deletePokemon(int id) throws IOException {
        cart.deletePokemon(id);
        PokemonDaoMem pokemonDaoMem = PokemonDaoMem.getInstance();
        JSONObject pokeJsonObject = pokemonDaoMem.getPokemonJsonObjectFromUrl("https://pokeapi.co/api/v2/pokemon/" + id); // changes for testing
        log.writeLog("Deleted specific pokemon species from cart", pokemonDaoMem.getPokemonFromJsonObject(pokeJsonObject));
    }

    @Override
    public HashMap<Pokemon, Integer> getAll() {
        return cart.getPokemons();
    }

    public int getTotalPrice() {
        int sum = 0;
        for (Map.Entry<Pokemon, Integer> entry : this.getAll().entrySet()) {
            sum += entry.getKey().getDefaultPrice() * entry.getValue();
        }
        return sum;
    }

    public int getTotalAmount() {
        int sum = 0;
        for (Map.Entry<Pokemon, Integer> entry : this.getAll().entrySet()) {
            sum += entry.getValue();
        }
        return sum;
    }

    public int getPokemonCount(int pokemonId) {
        return cart.getPokemonCount(pokemonId);
    }

}
