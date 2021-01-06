package com.codecool.shop.config;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

public class DataManager {

    public static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream inputStream = new FileInputStream("src/main/resources/connection.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public static String getDao() {
         Properties properties = loadProperties();
         return properties.getProperty("dao");
    }

    public static DataSource connectDataBase() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        Properties properties = loadProperties();

        try {
            dataSource.setDatabaseName(properties.getProperty("database"));
            dataSource.setUser(properties.getProperty("user"));
            dataSource.setPassword(properties.getProperty("password"));
            dataSource.getConnection().close();
        } catch (SQLException e) {
            System.out.println("DataBase connection failed!");
            e.printStackTrace();
        }

        return dataSource;
    }
}
