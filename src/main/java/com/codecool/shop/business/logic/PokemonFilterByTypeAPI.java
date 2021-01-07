package com.codecool.shop.business.logic;

import com.codecool.shop.config.DataHandlerConfig;
import com.codecool.shop.dao.UtilDao;
import com.codecool.shop.dao.implementation.PokemonDaoMem;
import com.codecool.shop.dao.implementation.PokemonFilterDaoByTypeDao;
import com.codecool.shop.model.Pokemon;
import com.codecool.shop.model.PokemonCategory;
import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/filter-by-type"}) // query parameters: must have: type name or id, optional: offset and limit as query parameters
public class PokemonFilterByTypeAPI extends HttpServlet implements UtilDao {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        String type = request.getParameter("type"); // todo
        int offset = request.getParameter("offset") != null ? Integer.parseInt(request.getParameter("offset")) : 0;

//        PokemonFilterDaoByTypeDao filterDao = PokemonFilterDaoByTypeDao.getInstance(); // prev version
//        List<Pokemon> filteredPokemons = filterDao.getPokemons(type, offset);
//
//        PokemonDaoMem pokemonDaoMem = PokemonDaoMem.getInstance();
//        pokemonDaoMem.setData(filteredPokemons);
//
//        Gson gson = new Gson();
//
//        String filteredPokemonsJson = gson.toJson(filteredPokemons);

        String filteredPokemonJson = DataHandlerConfig.getPokemonDaoFilterByType(type, offset);

        out.println(filteredPokemonJson);
    }
}
