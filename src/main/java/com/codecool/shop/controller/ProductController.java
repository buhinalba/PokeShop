package com.codecool.shop.controller;

import com.codecool.shop.config.DataHandlerConfig;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.PokemonCategoryDao;
import com.codecool.shop.dao.PokemonDao;
import com.codecool.shop.dao.UtilDao;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet implements UtilDao {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        PokemonDao pokemonDao = DataHandlerConfig.getPokemonDao();
        PokemonCategoryDao pokemonCategoryDao = DataHandlerConfig.getPokemonCategoryDao();

//        int page = req.getParameter("page") != null ? Integer.parseInt(req.getParameter("page")) : 0; // prev version
        int offset = req.getParameter("offset") != null ? Integer.parseInt(req.getParameter("offset")) : 0;

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());

        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("pokemons", pokemonDao.getAll(offset));
        context.setVariable("types", pokemonCategoryDao.getAll());

        engine.process("product/main.html", context, resp.getWriter());
    }
}

