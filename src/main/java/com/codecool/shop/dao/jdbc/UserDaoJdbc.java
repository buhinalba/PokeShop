package com.codecool.shop.dao.jdbc;

import com.codecool.shop.config.DataManager;
import com.codecool.shop.dao.UserDao;
import com.codecool.shop.model.Customer;
import com.codecool.shop.model.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class UserDaoJdbc extends DataManager implements UserDao {
    DataSource dataSource;

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
        } catch (SQLException e) {
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
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
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

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Customer> getAll() {
        // is this even safe
        return null;
    }
}
