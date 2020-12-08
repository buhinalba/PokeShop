package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.PokemonCategoryDao;
import com.codecool.shop.dao.PokemonDao;
import com.codecool.shop.dao.UtilDao;
import com.codecool.shop.dao.implementation.PokemonCategoryDaoMem;
import com.codecool.shop.dao.implementation.PokemonDaoMem;
import com.codecool.shop.dao.implementation.PokemonGetAllDao;
import com.codecool.shop.model.Pokemon;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
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
        PokemonDao productDataStore = PokemonDaoMem.getInstance();
        PokemonCategoryDao productCategoryDataStore = PokemonCategoryDaoMem.getInstance();

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());

        System.out.println(pokemonGetAllDao.getAll());

        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("category", productCategoryDataStore.find(1));
        context.setVariable("products", productDataStore.getAll());
        // // Alternative setting of the template context
        // Map<String, Object> params = new HashMap<>();
        // params.put("category", productCategoryDataStore.find(1));
        // params.put("products", productDataStore.getBy(productCategoryDataStore.find(1)));
        // context.setVariables(params);
        engine.process("product/index.html", context, resp.getWriter());
    }
}
