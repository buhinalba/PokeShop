package com.codecool.shop.business.logic;

import com.codecool.shop.dao.UtilDao;
import com.codecool.shop.model.PokemonCategory;
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
        // todo separate this into dao and business layer ! (no api request happening here!)
        PrintWriter out = response.getWriter();


        String type = request.getParameter("type"); // todo
//        int offset = request.getParameter("offset") != null ? Integer.parseInt(request.getParameter("offset")) : null;
//        int limit = request.getParameter("offset") != null ? Integer.parseInt(request.getParameter("offset")) : null;

        HttpURLConnection con = UtilDao.getHttpUrlConnection("https://pokeapi.co/api/v2/type");
        String content = UtilDao.getResponse(con);
        con.disconnect();
        JSONObject jsonResponse = (JSONObject) JSONValue.parse(content);
        JSONArray responseTypes = (JSONArray) jsonResponse.get("results");
        String filteredTypeResponse = null;
        for (Object pokeType: responseTypes) {
            System.out.println(((JSONObject) pokeType).get("name"));
            if (((JSONObject) pokeType).get("name").equals(type)) {
                System.out.println("GOT IT");
                HttpURLConnection conType = UtilDao.getHttpUrlConnection(((JSONObject) pokeType).get("url").toString());
                filteredTypeResponse = UtilDao.getResponse(conType);
                conType.disconnect();
            }
        }
        out.println(filteredTypeResponse);

        List<PokemonCategory> filteredPokemonResponse = new ArrayList<>();   // todo create Category Instance

        // here a possible exception throwing -> could handle it in javascript ?
//        if (filteredTypeResponse != null) {
//            JSONObject TypeResponseJson = (JSONObject) JSONValue.parse(filteredTypeResponse);
//            JSONArray pokemonsOfType = (JSONArray) TypeResponseJson.get("pokemons");
//            // todo define offset and limit -> fori & .get() - should there even be offset/limit?
//            pokemonsOfType.size();
//            for (Object pokemonJson : pokemonsOfType) {
//                JSONObject poke = (JSONObject) ((JSONObject) pokemonJson).get("pokemon");
//                String pokeUrl = poke.get("url").toString();
//                HttpURLConnection pokeURL = UtilDao.getHttpUrlConnection(pokeUrl);
//                String pokeResponse = UtilDao.getResponse(pokeURL);
//                // convert pokeResponse into pokemon class
//                // add pokemon to category class
//            }
//
//        }

        // send back pokemon category in json format
    }


}
