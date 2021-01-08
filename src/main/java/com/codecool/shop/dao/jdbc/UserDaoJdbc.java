package com.codecool.shop.dao.jdbc;

import com.codecool.shop.config.DataManager;
import com.codecool.shop.dao.UserDao;
import com.codecool.shop.dao.jdbc.data.DataGeneratorJDBC;
import com.codecool.shop.model.Customer;
import com.codecool.shop.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class UserDaoJdbc extends DataManager implements UserDao {
    DataSource dataSource;
    private static final Logger logger = LoggerFactory.getLogger(UserDaoJdbc.class);
    public UserDaoJdbc() {
        this.dataSource = connectDataBase();
    }

    @Override
    public void add(Customer user) {
        if (!(user instanceof User)) throw new IllegalArgumentException("User must by of type User, not only customer!");

        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO \"USER\" (name, email, password) VALUES (?, ?, ?)";

            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, user.getName());
            st.setString(2, user.getEmail());
            st.setString(3, ((User)user).getPassword());
            st.executeUpdate();
            logger.info("User added.");
        } catch (SQLException e) {
            logger.warn("Cannot add user to database.");
            e.printStackTrace();
        }
    }

    @Override
    public User find(String email) {
        // todo question could use optional?
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT * from \"USER\" WHERE email = ?";

            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                User user = new User(rs.getString(2), rs.getString(3), rs.getString(4));
                user.setId(rs.getInt(1));
                logger.info("User is found with email: " + email);
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.warn("Cannot find User with email: " + email);
        }
        return null;
    }

    @Override
    public boolean emailExists(String email) {
        return find(email) != null;
    }

    @Override
    public Customer find(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT * from \"USER\" WHERE id = ?";

            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                User user = new User(rs.getString(2), rs.getString(3), rs.getString(4));
                user.setId(rs.getInt(1));
                logger.info("Customer found with id: " + id);
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.warn("Cannot find Customer with id: " + id);
        }
        return null;
    }

    @Override
    public void remove(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "DELETE from \"USER\" WHERE id = ?";

            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            st.executeUpdate();
            logger.info("Successfully removed User");
        } catch (SQLException e) {
            e.printStackTrace();
            logger.warn("Cannot remove User from database.");
        }
    }

    @Override
    public List<Customer> getAll() {
        // is this even safe
        return null;
    }
}
