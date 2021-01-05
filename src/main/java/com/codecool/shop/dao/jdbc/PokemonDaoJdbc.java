package com.codecool.shop.dao.jdbc;

import com.codecool.shop.dao.PokemonDao;
import com.codecool.shop.model.Pokemon;
import com.codecool.shop.model.PokemonCategory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PokemonDaoJdbc implements PokemonDao {
    private DataSource dataSource;

    public PokemonDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Pokemon pokemon) {
        try(Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO pokemon (id, name, default_price, pokemon_category, sprite) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, pokemon.getId());
            st.setString(2, pokemon.getName());
            st.setDouble(3, pokemon.getDefaultPrice());
            st.setArray(4, (Array) pokemon.getPokemonCategory());
            st.setString(5, pokemon.getSpriteImageUrl());
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            rs.next();
            pokemon.setId(rs.getInt(1));

        } catch (SQLException throwables) {
            throw new RuntimeException("Error while adding new Pokemon.", throwables);
        }
    }


    @Override
    public Pokemon find(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT name, default_price, pokemon_category, sprite FROM pokemon WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                return null;
            }
            Pokemon pokemon = new Pokemon(id, rs.getString(1), rs.getInt(2), Arrays.asList(rs.getString(3)), rs.getString(4));
            return pokemon;
        } catch (SQLException e) {
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
        } catch (SQLException e) {
            throw new RuntimeException("Error while deleting pokemon with id: " + id, e);
        }
    }

    @Override
    public List<Pokemon> getAll() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, name, default_price, pokemon_category, sprite FROM pokemon";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            List<Pokemon> result = new ArrayList<>();
            while (rs.next()) {
                Pokemon pokemon = new Pokemon(rs.getInt(1), rs.getString(2), rs.getInt(3),
                        Arrays.asList(rs.getString(4)), rs.getString(5));
                result.add(pokemon);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading all pokemons", e);
        }
    }

    public List<Pokemon> getBy(PokemonCategory pokemonCategory){
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, name, default_price, pokemon_category, sprite FROM pokemon WHERE pokemon_category = ? ";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            List<Pokemon> result = new ArrayList<>();
            while (rs.next()) {
                Pokemon pokemon = new Pokemon(rs.getInt(1), rs.getString(2), rs.getInt(3),
                        Arrays.asList(rs.getString(4)), rs.getString(5));
                result.add(pokemon);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading all pokemons", e);
        }
    }
}