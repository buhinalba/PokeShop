package com.codecool.shop.dao.data;

import com.codecool.shop.config.DataManager;
import com.codecool.shop.dao.PokemonCategoryDao;
import com.codecool.shop.dao.PokemonGetAllDaoInt;
import com.codecool.shop.dao.implementation.PokemonCategoryDaoMem;
import com.codecool.shop.dao.implementation.PokemonGetAllDao;
import com.codecool.shop.model.Pokemon;
import com.codecool.shop.model.PokemonCategory;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DataGeneratorJDBC {
    private static PokemonGetAllDaoInt pokemonGetAllDao = new PokemonGetAllDao();

    private static final String apiURL = "https://pokeapi.co/api/v2/pokemon?offset=0&limit=1118";

    public static void addPokemonsToDatabase() throws IOException {
        DataSource dataSource = DataManager.connectDataBase();
        List<Pokemon> pokemons = pokemonGetAllDao.getAll(apiURL);

        for (Pokemon pokemon: pokemons) {
            try (Connection conn = dataSource.getConnection()){
                String query = "INSERT INTO pokemon VALUES (?, ?, ?, ?)";
                PreparedStatement st = conn.prepareStatement(query);
                st.setInt(1, pokemon.getId());
                st.setString(2, pokemon.getName());
                st.setInt(3, (int) pokemon.getDefaultPrice());
                st.setString(4, pokemon.getSpriteImageUrl());
                st.executeUpdate();

                connectCategoriesToPokemons(conn, pokemon.getPokemonCategory(), pokemon.getId());
            } catch (SQLException e) {
                System.out.println("Pokemon addition to database failed");
                e.printStackTrace();
            }
        }
    }


    public static void addCategoriesToDatabase() throws IOException {
        DataSource dataSource = DataManager.connectDataBase();

        PokemonCategoryDao categoryDao = new PokemonCategoryDaoMem();
        List<String> categories = categoryDao.getFullCategoryNameList();
        for (String categoryName : categories) {
            try (Connection conn = dataSource.getConnection()) {
                String query = "INSERT INTO category VALUES (DEFAULT, ?)";
                PreparedStatement st = conn.prepareStatement(query);
                st.setString(1, categoryName);
                st.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public static void connectCategoriesToPokemons(Connection conn, List<String> categories, int pokemonId) throws IOException {
        for(String category: categories) {
            int id = getIdFromCategoryName(category);

        }
    }

    private static int getIdFromCategoryName(String name) {
        return -1;
    }


    public static void main(String[] args) {
        try {
            addCategoriesToDatabase();
            addPokemonsToDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
