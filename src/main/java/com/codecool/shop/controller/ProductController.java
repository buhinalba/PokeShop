package com.codecool.shop.controller;


import com.codecool.shop.business.logic.SessionController;

import com.codecool.shop.config.DataManager;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.PokemonCategoryDao;
import com.codecool.shop.dao.PokemonDao;
import com.codecool.shop.dao.UtilDao;

import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.model.User;

import com.codecool.shop.dao.implementation.PokemonCategoryDaoMem;
import com.codecool.shop.dao.implementation.PokemonDaoMem;
import com.codecool.shop.dao.implementation.PokemonGetAllDao;
import com.codecool.shop.dao.jdbc.PokemonCategoryDaoJdbc;
import com.codecool.shop.dao.jdbc.PokemonDaoJdbc;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import javax.xml.crypto.Data;
import java.io.IOException;


@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet implements UtilDao {

    private PokemonGetAllDao pokemonGetAllDao = new PokemonGetAllDao();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        SessionController sessionController = SessionController.getInstance();
        User user = sessionController.getUser(session.getId());

        if (user != null) {
            System.out.println("logged in: " + user.getEmail());
        }

        PokemonDaoMem pokemonDaoMem = PokemonDaoMem.getInstance();
        PokemonCategoryDaoMem pokemonCategoryDaoMem = PokemonCategoryDaoMem.getInstance();

        PokemonDao pokemonDao;
        PokemonCategoryDao pokemonCategoryDao;
        int page = req.getParameter("page") != null ? Integer.parseInt(req.getParameter("page")) : 0;

        if (DataManager.getDao().equals("memory")) {
            pokemonDao = PokemonDaoMem.getInstance();
            pokemonCategoryDao = PokemonCategoryDaoMem.getInstance();

            String currentPage = "https://pokeapi.co/api/v2/pokemon/?offset=0&limit=20";
            pokemonGetAllDao.addAllPokemonsToPokemonDaoMem(currentPage);
        } else {
            pokemonDao = new PokemonDaoJdbc(DataManager.connectDataBase());
            pokemonCategoryDao = new PokemonCategoryDaoJdbc(DataManager.connectDataBase());
        }


        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());

        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("pokemons", pokemonDao.getAll(page));
        context.setVariable("types", pokemonCategoryDao.getAll());

        engine.process("product/main.html", context, resp.getWriter());
    }
}

