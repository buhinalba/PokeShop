package com.codecool.shop.mem.dao.test;

import com.codecool.shop.dao.implementation.PokemonDaoMem;
import com.codecool.shop.model.Pokemon;
import com.codecool.shop.model.PokemonCategory;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.mock;

class PokemonDaoMemTest {
    static final PokemonDaoMem pokemonDaoMem = PokemonDaoMem.getInstance();
    static final String url = "https://pokeapi.co/api/v2/pokemon/1/";
    Pokemon pokemon = new Pokemon(123, "Charmander", 20, Arrays.asList("normal", "fire"), "sprite");
    Pokemon pokemonTest_II = new Pokemon(124, "Charmander2", 20, Arrays.asList("normal", "fire"), "sprite");


    @Test
    void getPokemonJsonObjectFromUrl_validPokemonUrl_returnsPokemonJsonObject() throws IOException {
        assertTrue(pokemonDaoMem.getPokemonJsonObjectFromUrl(url) instanceof JSONObject);
    }

    @Test
    void getPokemonFromUrl_validPokemonUrl_returnsPokemonWithName() throws IOException {
        JSONObject jsonObject = pokemonDaoMem.getPokemonJsonObjectFromUrl(url);
        Pokemon pokemon = pokemonDaoMem.getPokemonFromJsonObject(jsonObject);
        assertEquals(pokemon.getName(), "bulbasaur");
    }

    @Test
    void getPokemonById_validPokemonInData_returnsPokemon() {
        pokemonDaoMem.clearMem();
        pokemonDaoMem.add(pokemon);
        assertEquals(pokemon, pokemonDaoMem.getPokemonById(pokemon.getId()));
    }

    @Test
    void getPokemonById_invalidPokemonInData_throwsNoSuchElementException() {
        assertThrows(NoSuchElementException.class, () -> pokemonDaoMem.getPokemonById(00000));
    }

    @Test
    void getPokemonByCategory_validPokemonsInData_returnsListPokemon() {
        pokemonDaoMem.clearMem();
        pokemonDaoMem.add(pokemon);
        pokemonDaoMem.add(pokemonTest_II);
        assertEquals(2, pokemonDaoMem.getBy(new PokemonCategory("normal")).size());
    }

    @Test
    void getPokemonByCategory_noPokemonInData_returnsZero() {
        assertEquals(0, pokemonDaoMem.getBy(new PokemonCategory("normal")).size());
    }

}

