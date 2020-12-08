package com.codecool.shop.controller;

import com.codecool.shop.dao.PokemonCategoryDao;
import com.codecool.shop.dao.PokemonDao;
import com.codecool.shop.dao.UtilDao;
import com.codecool.shop.dao.implementation.PokemonCategoryDaoMem;
import com.codecool.shop.dao.implementation.PokemonDaoMem;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.Pokemon;
import com.codecool.shop.model.PokemonCategory;
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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet implements UtilDao {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PokemonDao productDataStore = PokemonDaoMem.getInstance();
        PokemonCategoryDao productCategoryDataStore = PokemonCategoryDaoMem.getInstance();

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());

        HttpURLConnection connection = UtilDao.getHttpUrlConnection("https://pokeapi.co/api/v2/pokemon/");
        String resultString = UtilDao.getResponse(connection);
        connection.disconnect();

        List<Pokemon> pokemonList = new ArrayList<>();

        JSONObject jsonResponse = (JSONObject) JSONValue.parse(resultString);
        JSONArray jsonArray = (JSONArray) jsonResponse.get("results");
        for (Object poki : jsonArray) {
            HttpURLConnection connectionPokemon = UtilDao.getHttpUrlConnection(((JSONObject) poki).get("url").toString());
            String pokemon = UtilDao.getResponse(connectionPokemon);
            connectionPokemon.disconnect();

            JSONObject pokemonJsonObject = (JSONObject) JSONValue.parse(pokemon);
            Integer pokemonId = (Integer) pokemonJsonObject.get("id");
            String pokemonName = (String) pokemonJsonObject.get("name");
            Long pokemonPrice = (Long) pokemonJsonObject.get("base_experience");
            String pokemonSprite = (String) ((JSONObject) pokemonJsonObject.get("sprites")).get("front_default");
            JSONArray pokemonCategories = (JSONArray) pokemonJsonObject.get("types");
            List<String> pokemonCategoryNames = new ArrayList<>();
            for (Object category :pokemonCategories) {
                String pokemonCategoryName = (String) ((JSONObject) ((JSONObject) category).get("type")).get("name");
                pokemonCategoryNames.add(pokemonCategoryName);
            }

            pokemonList.add(new Pokemon(pokemonId, pokemonName, pokemonPrice, pokemonCategoryNames, pokemonSprite));
        }




        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("category", productCategoryDataStore.find(1));
        context.setVariable("products", productDataStore.getAll());
        // // Alternative setting of the template context
        // Map<String, Object> params = new HashMap<>();
        // params.put("category", productCategoryDataStore.find(1));
        // params.put("products", productDataStore.getBy(productCategoryDataStore.find(1)));
        // context.setVariables(params);
        engine.process("product/main.html", context, resp.getWriter());
    }

}
