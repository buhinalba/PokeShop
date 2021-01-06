package com.codecool.shop.mem.dao.test;

import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Pokemon;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CartDaoMemTest {

    @Test
    void findPokemon_validPokemonId_returnsEqualsWithPokemon() {
        Pokemon pokemon = new Pokemon(123, "Charmander", 20, Arrays.asList("normal", "fire!"), "sprite");
        CartDaoMem cartDaoMem = CartDaoMem.getInstance();
        cartDaoMem.add(pokemon); // add random pokemon to cart
        assertEquals(pokemon, cartDaoMem.findPokemon(pokemon.getId()));
    }

    @Test
    void findPokemon_invalidPokemonId_throwsNoSuchElementException() {
        int fakePokemonId = 612;
        CartDaoMem cartDaoMem = CartDaoMem.getInstance();
        assertThrows(NoSuchElementException.class, () -> cartDaoMem.findPokemon(fakePokemonId));
    }

    @Test
    void getTotalPrice_existingPokemonInCart_returnsTotalPrice() {
        Pokemon pokemon = new Pokemon(123, "Charmander", 666, Arrays.asList("normal", "fire!"), "sprite");
        CartDaoMem cartDaoMem = CartDaoMem.getInstance();
        cartDaoMem.add(pokemon);
        assertEquals(pokemon.getDefaultPrice(), cartDaoMem.getTotalPrice());
    }

    @Test
    void getTotalPrice_existingMultiplePokemonInCart_returnsTotalPrice() {
        Pokemon pokemon = new Pokemon(123, "Charmander", 666, Arrays.asList("normal", "fire!"), "sprite");
        CartDaoMem cartDaoMem = CartDaoMem.getInstance();
        cartDaoMem.add(pokemon);
        cartDaoMem.add(pokemon);
        assertEquals(pokemon.getDefaultPrice() * 2, cartDaoMem.getTotalPrice());
    }

    @Test
    void getTotalAmount_existingMultiplePokemonInCart_returnsTotalAmount() {
        Pokemon pokemon = new Pokemon(123, "Charmander", 666, Arrays.asList("normal", "fire!"), "sprite");
        CartDaoMem cartDaoMem = CartDaoMem.getInstance();
        cartDaoMem.add(pokemon);
        cartDaoMem.add(pokemon);
        cartDaoMem.add(pokemon);
        assertEquals(3, cartDaoMem.getTotalAmount());
    }

    @Test
    void getTotalAmount_nonExistingPokemonInCart_returnsZero() {
        CartDaoMem cartDaoMem = CartDaoMem.getInstance();
        assertEquals(cartDaoMem.getTotalAmount(), 0);
    }

    @Test
    void getAll_existingPokemonInCart_returnPokemonIntegerHashMap() {
        Pokemon pokemon = new Pokemon(123, "Charmander", 666, Arrays.asList("normal", "fire!"), "sprite");
        HashMap<Pokemon, Integer> pokemonIntegerHashMap = new HashMap<>(Map.ofEntries(Map.entry(pokemon, 1))) ;
        CartDaoMem cartDaoMem = CartDaoMem.getInstance();
        cartDaoMem.add(pokemon);
        assertEquals(cartDaoMem.getAll(), pokemonIntegerHashMap);
    }
}
