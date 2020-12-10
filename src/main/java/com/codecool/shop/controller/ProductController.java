package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.UtilDao;
import com.codecool.shop.dao.implementation.PokemonCategoryDaoMem;
import com.codecool.shop.dao.implementation.PokemonDaoMem;
import com.codecool.shop.dao.implementation.PokemonGetAllDao;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet implements UtilDao {

    private PokemonGetAllDao pokemonGetAllDao = new PokemonGetAllDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PokemonDaoMem pokemonDaoMem = PokemonDaoMem.getInstance();
        PokemonCategoryDaoMem pokemonCategoryDaoMem = PokemonCategoryDaoMem.getInstance();

        String currentPage = "https://pokeapi.co/api/v2/pokemon/?offset=0&limit=20";

        String page = req.getParameter("page");

        pokemonGetAllDao.addAllPokemonsToPokemonDaoMem(currentPage);

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());

        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("pokemons", pokemonDaoMem.getAll());
        context.setVariable("types", pokemonCategoryDaoMem.getAll());

        engine.process("product/main.html", context, resp.getWriter());
    }
}
