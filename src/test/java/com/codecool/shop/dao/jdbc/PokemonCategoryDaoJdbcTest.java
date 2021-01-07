package com.codecool.shop.dao.jdbc;

import com.codecool.shop.config.DataManager;
import com.codecool.shop.dao.PokemonCategoryDao;
import com.codecool.shop.model.PokemonCategory;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PokemonCategoryDaoJdbcTest {
    PokemonCategoryDao testCategoryDao = new PokemonCategoryDaoJdbc(DataManager.connectDataBase());
    final int ID = 21;            // I only know this from checking the db manually

    @Test
    void add() {
        PokemonCategory pokemonCategory = new PokemonCategory("test category");
        testCategoryDao.add(pokemonCategory);
        PokemonCategory categoryRes = testCategoryDao.find(ID);
        assertEquals(pokemonCategory.getCategory(), categoryRes.getCategory());

        testCategoryDao.remove(ID);
    }

    @Test
    void find() {
        PokemonCategory pokemonCategory = new PokemonCategory("test category");
        testCategoryDao.add(pokemonCategory);

        assertNotEquals(null, testCategoryDao.find(ID));

        testCategoryDao.remove(ID);
    }

    @Test
    void remove() {
        PokemonCategory pokemonCategory = new PokemonCategory("test category");
        testCategoryDao.add(pokemonCategory);
        testCategoryDao.remove(ID);

        assertNull(testCategoryDao.find(ID));

    }


    @Test
    void getFullCategoryNameList() throws IOException {
        String categoryString = "normal\n" +
                "fighting\n" +
                "flying\n" +
                "poison\n" +
                "ground\n" +
                "rock\n" +
                "bug\n" +
                "ghost\n" +
                "steel\n" +
                "fire\n" +
                "water\n" +
                "grass\n" +
                "electric\n" +
                "psychic\n" +
                "ice\n" +
                "dragon\n" +
                "dark\n" +
                "fairy\n" +
                "unknown\n" +
                "shadow\n";
        String[] categoryNames = categoryString.split("\n");

        List<String> categoryList = new ArrayList<>(Arrays.asList(categoryNames));
        assertEquals(categoryList, testCategoryDao.getFullCategoryNameList());
    }
}