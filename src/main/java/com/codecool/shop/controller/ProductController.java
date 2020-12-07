package com.codecool.shop.controller;

import com.codecool.shop.dao.PokemonCategoryDao;
import com.codecool.shop.dao.PokemonDao;
import com.codecool.shop.dao.implementation.PokemonCategoryDaoMem;
import com.codecool.shop.dao.implementation.PokemonDaoMem;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.Pokemon;
import com.codecool.shop.model.PokemonCategory;
import com.google.gson.Gson;
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
import java.io.ObjectInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {

    private HttpURLConnection getHttpUrlConnection (String apiEndpoint) throws IOException {
        URL pokemonUrl = new URL(apiEndpoint);
        HttpURLConnection connection = (HttpURLConnection) pokemonUrl.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");
        return connection;
    }

    private String getResponse(HttpURLConnection connection) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        return content.toString();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PokemonDao productDataStore = PokemonDaoMem.getInstance();
        PokemonCategoryDao productCategoryDataStore = PokemonCategoryDaoMem.getInstance();

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());

        HttpURLConnection connection = getHttpUrlConnection("https://pokeapi.co/api/v2/pokemon/");
        String resultString = getResponse(connection);
        connection.disconnect();

        List<Pokemon> pokemonList = new ArrayList<>();

        JSONObject jsonResponse = (JSONObject) JSONValue.parse(resultString);
        JSONArray jsonArray = (JSONArray) jsonResponse.get("results");
        for (Object poki : jsonArray) {
            HttpURLConnection connectionPokemon = getHttpUrlConnection(((JSONObject) poki).get("url").toString());
            String pokemon = getResponse(connectionPokemon);
            connectionPokemon.disconnect();
            JSONObject pokemonJsonObject = (JSONObject) JSONValue.parse(pokemon);
            String pokemonName = (String) pokemonJsonObject.get("name");
            float pokemonPrice = (float) pokemonJsonObject.get("base_experience");
            String pokemonSprite = (String) pokemonJsonObject.get("name");

            // UUID for id? also maybe store sprite url as field in poke
            int id = 1;
            PokemonCategory asdPokemonCategory = new PokemonCategory("ASD", "asd");
            pokemonList.add(new Pokemon(id, pokemonName, pokemonPrice, "PokeCoin", asdPokemonCategory));
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
