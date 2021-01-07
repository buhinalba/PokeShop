package com.codecool.shop.dao.jdbc;

import com.codecool.shop.config.DataManager;
import com.codecool.shop.model.Pokemon;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PokemonDaoJdbcTest {
    PokemonDaoJdbc testJDBC = new PokemonDaoJdbc(DataManager.connectDataBase());

    @Test
    void addPokemon_asNull_shouldThrowException() {
        assertThrows(RuntimeException.class, () ->testJDBC.add(null));
    }

    @Test
    void addPokemon_withValues_shouldInsertNewRow() {
        int id = 10000;
        Pokemon pokemon = new Pokemon(id, "test pokemon", 0, new ArrayList<>(), "no image");
        testJDBC.add(pokemon);
        assertEquals(pokemon, testJDBC.find(id));   // this is also testing find
        testJDBC.remove(id);
    }

    @Test
    void findPokemon_shouldReturnPokemon() {
        int pokemonId = 1;
        Pokemon pokemon = testJDBC.find(pokemonId);
        assertEquals(pokemonId, pokemon.getId());   // this is also testing find
        assertEquals("bulbasaur", pokemon.getName());
        assertEquals("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png", pokemon.getSpriteImageUrl());
        assertEquals(64, (int) pokemon.getDefaultPrice());
    }

    @Test
    void removePokemon_shouldDecreaseRowCount() {
        int id = 10000;
        Pokemon pokemon = new Pokemon(id, "test pokemon", 0, new ArrayList<>(), "no image");
        testJDBC.add(pokemon);
        testJDBC.remove(id);
        assertNull(testJDBC.find(id));   // this is also testing find
    }

    @Test
    void getAll_ReturnsAllRowsFromDb() {
        int pokemonTableSize = 1118;
        List<Pokemon> allPoke = testJDBC.getAll();
        assertEquals(pokemonTableSize, allPoke.size());
    }

}
