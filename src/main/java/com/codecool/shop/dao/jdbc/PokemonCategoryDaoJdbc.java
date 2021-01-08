package com.codecool.shop.dao.jdbc;

import com.codecool.shop.dao.PokemonCategoryDao;
import com.codecool.shop.dao.jdbc.data.DataGeneratorJDBC;
import com.codecool.shop.model.PokemonCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PokemonCategoryDaoJdbc implements PokemonCategoryDao {
    private DataSource dataSource;
    private static final Logger logger = LoggerFactory.getLogger(PokemonCategoryDaoJdbc.class);
    public PokemonCategoryDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(PokemonCategory category) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO category (id, name) VALUES (?, ?)";
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, category.getId());
            st.setString(2, category.getName());
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            rs.next();
            category.setId(rs.getInt(1));
            logger.info("Category added successfully.");
        } catch (SQLException throwables) {
            logger.warn("Cannot add category to database.");
            throw new RuntimeException("Error while adding new Category.", throwables);
        }
    }

    @Override
    public PokemonCategory find(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT name FROM category WHERE category.id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                return null;
            }
            PokemonCategory pokemonCategory = new PokemonCategory(rs.getString(1));
            logger.info("Category name found.");
            return pokemonCategory;
        } catch (SQLException e) {
            logger.warn("Cannot load category.");
            throw new RuntimeException("Error while reading pokemon category with id: " + id, e);
        }
    }

    @Override
    public void remove(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "DELETE FROM category WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            st.executeQuery();
            logger.info("Category removed.");
        } catch (SQLException e) {
            logger.warn("Cannot remove category.");
            throw new RuntimeException("Error while deleting category with id: " + id, e);
        }
    }

    @Override
    public List<PokemonCategory> getAll() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT name FROM category";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            List<PokemonCategory> result = new ArrayList<>();
            while (rs.next()) {
                PokemonCategory pokemonCategory = new PokemonCategory(rs.getString(1));
                result.add(pokemonCategory);
            }
            logger.info("All categories loaded.");
            return result;
        } catch (SQLException e) {
            logger.warn("Cannot load all pokemon categories.");
            throw new RuntimeException("Error while reading all pokemon categories", e);
        }
    }

    @Override
    public List<String> getFullCategoryNameList() {
        List<String> pokemonCategoriesString = new ArrayList<>();
        getAll().forEach(pokemonCategory -> pokemonCategoriesString.add(pokemonCategory.getCategory()));
        return pokemonCategoriesString;
    }
}
