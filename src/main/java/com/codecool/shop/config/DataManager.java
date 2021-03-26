package com.codecool.shop.config;

import org.postgresql.ds.PGSimpleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

public class DataManager {
    private static final Logger logger = LoggerFactory.getLogger(DataManager.class);

    public static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream inputStream = new FileInputStream("src/main/resources/connection.properties")) {
            properties.load(inputStream);
            logger.info("Properties loaded.");
        } catch (IOException e) {
            e.printStackTrace();
            logger.warn("Cannot load properties.");

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
            logger.info("Database connected.");
        } catch (SQLException e) {
            e.printStackTrace();
            logger.warn("DataBase connection failed!");
        }

        return dataSource;
    }
}
