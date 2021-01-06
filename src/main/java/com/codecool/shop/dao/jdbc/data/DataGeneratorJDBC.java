package com.codecool.shop.dao.jdbc.data;

import com.codecool.shop.config.DataManager;
import com.codecool.shop.dao.PokemonCategoryDao;
import com.codecool.shop.dao.PokemonGetAllDaoInt;
import com.codecool.shop.dao.implementation.PokemonCategoryDaoMem;
import com.codecool.shop.dao.implementation.PokemonGetAllDao;
import com.codecool.shop.model.Pokemon;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

public class DataGeneratorJDBC {
    private static PokemonGetAllDaoInt pokemonGetAllDao = new PokemonGetAllDao();
    private static DataSource dataSource = DataManager.connectDataBase();

    private static final String apiURL = "https://pokeapi.co/api/v2/pokemon?offset=0&limit=1118";

    public static void addPokemonsToDatabase() throws IOException {
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
                break;
            }
        }
    }


    public static void addCategoriesToDatabase() throws IOException {
        PokemonCategoryDao categoryDao = new PokemonCategoryDaoMem();
        List<String> categories = categoryDao.getFullCategoryNameList();
        int id = 1;
        try (Connection conn = dataSource.getConnection()) {
            for (String categoryName : categories) {
                String query = "INSERT INTO category (id, name) VALUES (?, ?)";
                PreparedStatement st = conn.prepareStatement(query);
                st.setInt(1, id++);
                st.setString(2, categoryName);
                st.execute();
            }
        } catch (SQLException e) {
                e.printStackTrace();
            }
    }


    public static void connectCategoriesToPokemons(Connection conn, List<String> categories, int pokemonId) throws SQLException {
        for(String category: categories) {
            int categoryId = getIdFromCategoryName(category);
            String query = "INSERT INTO pokemon_category VALUES (?, ?)";
            PreparedStatement st = conn.prepareStatement(query);
            st.setInt(1, pokemonId);
            st.setInt(2, categoryId);
            st.executeUpdate();
        }
    }

    private static int getIdFromCategoryName(String name) {
        try (Connection conn = dataSource.getConnection()) {
            String query = "SELECT id FROM category WHERE name = ?";
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, name);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                throw new SQLException("Category Does NOt Exist in database!");
            }
            return rs.getInt(1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        throw new NoSuchElementException("Category Does NOt Exist in database!");
    }

    public static void resetDataBase() {
        try (Connection conn = dataSource.getConnection()) {
            String query = "DELETE from pokemon_category; DELETE from category; DELETE from pokemon";
            PreparedStatement s = conn.prepareStatement(query);
            s.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        try {
            resetDataBase();
            addCategoriesToDatabase();
            addPokemonsToDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
