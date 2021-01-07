package com.codecool.shop.dao.jdbc;

import com.codecool.shop.dao.PokemonDao;
import com.codecool.shop.dao.jdbc.data.DataGeneratorJDBC;
import com.codecool.shop.model.Pokemon;
import com.codecool.shop.model.PokemonCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PokemonDaoJdbc implements PokemonDao {
    private DataSource dataSource;
    public static final int LIMIT = 20;
    private static final Logger logger = LoggerFactory.getLogger(PokemonDaoJdbc.class);
    public PokemonDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Pokemon pokemon) throws RuntimeException{
        try(Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO pokemon (id, name, price, sprite_url) VALUES (?, ?, ?, ?)";
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, pokemon.getId());
            st.setString(2, pokemon.getName());
            st.setDouble(3, pokemon.getDefaultPrice());
            st.setString(4, pokemon.getSpriteImageUrl());
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            rs.next();
            pokemon.setId(rs.getInt(1));
            logger.info("Pokemon added to database.");
        } catch (SQLException throwables) {
            logger.warn("Cannot add pokemon to database.");
            throw new RuntimeException("Error while adding new Pokemon.", throwables);
        } catch (NullPointerException e) {
            logger.error("Cannot set up database connection.");
            throw new RuntimeException("Pokemon is null");
        }
    }


    @Override
    public Pokemon find(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT p.name, price, CONCAT_WS(',' , ct.name) AS pokemon_category, sprite_url FROM pokemon AS p " +
                    "JOIN pokemon_category AS pc ON pc.pokemon_id = p.id " +
                    "JOIN category AS ct ON ct.id = pc.category_id " +
                    "WHERE p.id = ?";

            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                return null;
            }
            Pokemon pokemon = new Pokemon(id, rs.getString(1), rs.getInt(2), Collections.singletonList(rs.getString(3)), rs.getString(4));
            logger.info("Pokemon found with id: " + id);
            return pokemon;
        } catch (SQLException e) {
            logger.warn("Cannot found pokemon with id: " + id);
            throw new RuntimeException("Error while reading pokemon with id: " + id, e);
        }
    }

    @Override
    public void remove(int id){
        try (Connection conn = dataSource.getConnection()) {
            String sql = "DELETE FROM pokemon WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            logger.info("Pokemon removed successfully.");
        } catch (SQLException e) {
            logger.warn("Cannot remove pokemon.");
            throw new RuntimeException("Error while deleting pokemon with id: " + id, e);
        }
    }

    @Override
    public List<Pokemon> getAll(int offset) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT p.id," +
                         "       p.name, p.price, " +
                         "       string_agg(ct.name, ', ') AS pokemon_category, " +
                         "       sprite_url FROM pokemon AS p " +
                         "JOIN pokemon_category AS pc ON pc.pokemon_id = p.id " +
                         "JOIN category AS ct ON ct.id = pc.category_id " +
                         "GROUP BY p.id, p.name " +
                         "ORDER BY p.id " +
                         "OFFSET ?" +
                         "LIMIT " + LIMIT ;
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, offset);
            ResultSet rs = preparedStatement.executeQuery();
            logger.info("Selected all pokemon from database successfully.");
            return getPokemonsFromResultSet(rs);
        } catch (SQLException e) {
            logger.warn("Cannot load all pokemons.");
            throw new RuntimeException("Error while reading all pokemons", e);
        }
    }

    @Override
    public List<Pokemon> getAll() {
        return null;
    }

    @Override
    public List<Pokemon> getBy(PokemonCategory pokemonCategory) {
        return null;
    }

    @Override
    public List<Pokemon> getBy(PokemonCategory pokemonCategory, int offset){
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, name, price, string_agg(ct.name , ', ') AS pokemon_category, sprite_url FROM pokemon AS p " +
                    "JOIN pokemon_category AS pc ON  pc.pokemon_id = p.id " +
                    "JOIN category as ct ON pc.category_id = ct.id " +
                    "WHERE ct.name = ?  GROUP BY p.id " +
                    "OFFSET ? " +
                    "LIMIT ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, offset);
            st.setInt(2, LIMIT);
            ResultSet rs = st.executeQuery();
            logger.info("Get pokemons by category successfully.");
            return getPokemonsFromResultSet(rs);
        } catch (SQLException e) {
            logger.warn("Cannot load pokemons by category.");
            throw new RuntimeException("Error while reading all pokemons", e);
        }
    }

    private List<Pokemon> getPokemonsFromResultSet(ResultSet rs) throws SQLException {
        List<Pokemon> result = new ArrayList<>();
        while (rs.next()) {
            Pokemon pokemon = new Pokemon(rs.getInt(1), rs.getString(2), rs.getInt(3),
                    Collections.singletonList(rs.getString(4)), rs.getString(5));
            result.add(pokemon);
        }
        return result;
    }
}