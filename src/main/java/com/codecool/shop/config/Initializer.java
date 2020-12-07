package com.codecool.shop.config;

import com.codecool.shop.dao.PokemonCategoryDao;
import com.codecool.shop.dao.PokemonDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.PokemonCategoryDaoMem;
import com.codecool.shop.dao.implementation.PokemonDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Pokemon;
import com.codecool.shop.model.PokemonCategory;
import com.codecool.shop.model.Supplier;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Initializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        PokemonDao productDataStore = PokemonDaoMem.getInstance();
        PokemonCategoryDao productCategoryDataStore = PokemonCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        //setting up a new supplier
        Supplier amazon = new Supplier("Amazon", "Digital content and services");
        supplierDataStore.add(amazon);
        Supplier lenovo = new Supplier("Lenovo", "Computers");
        supplierDataStore.add(lenovo);

        //setting up a new product category
        PokemonCategory tablet = new PokemonCategory("Tablet", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDataStore.add(tablet);

        //setting up products and printing it
        productDataStore.add(new Pokemon(1, "Amazon Fire", 49.9f, "USD", tablet, "."));
        productDataStore.add(new Pokemon(2, "Lenovo IdeaPad Miix 700", 479, "USD",  tablet,"."));
        productDataStore.add(new Pokemon(3, "Amazon Fire HD 8", 89, "USD",  tablet, "."));
    }
}
