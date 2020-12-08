package com.codecool.shop.business.logic;

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
public class PokemonFilterByTypeAPI extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // todo separate this into dao and business layer ! (no api request happening here!)
        PrintWriter out = response.getWriter();

        String type = request.getParameter("type");
        int offset = Integer.parseInt(request.getParameter("offset"));
        int limit = Integer.parseInt(request.getParameter("offset"));

        HttpURLConnection con = getConnection("https://pokeapi.co/api/v2/type");
        String content = getResponse(con);
        con.disconnect();
        JSONObject jsonResponse = (JSONObject) JSONValue.parse(content);
        JSONArray responseTypes = (JSONArray) jsonResponse.get("results");
        String filteredTypeResponse = null;
        for (Object pokeType: responseTypes) {
            System.out.println(((JSONObject) pokeType).get("name"));
            if (((JSONObject) pokeType).get("name").equals(type)) {
                System.out.println("GOT IT");
                HttpURLConnection conType = getConnection(((JSONObject) pokeType).get("url").toString());
                filteredTypeResponse = getResponse(conType);
                conType.disconnect();
            }
        }

        List<PokemonCategory> filteredPokemonResponse = new ArrayList<>();   // todo create Category Instance

        // here a possible exception throwing -> could handle it in javascript ?
        if (filteredTypeResponse != null) {
            JSONObject TypeResponseJson = (JSONObject) JSONValue.parse(filteredTypeResponse);
            JSONArray pokemonsOfType = (JSONArray) TypeResponseJson.get("pokemons");
            // todo define offset and limit -> fori & .get() - should there even be offset/limit?
            for (Object pokemonJson: pokemonsOfType) {
                JSONObject poke = (JSONObject) ((JSONObject) pokemonJson).get("pokemon");
                String pokeUrl = poke.get("url").toString();
                HttpURLConnection pokeURL = getConnection(pokeUrl);
                String pokeResponse = getResponse(pokeURL);
                // convert pokeResponse into pokemon class
                // add pokemon to category class
            }
        }

        // send back pokemon category in json format

    }


    private HttpURLConnection getConnection(String urlString) throws IOException {
        URL url = new URL(urlString);                        //create url to send request to
        HttpURLConnection con = (HttpURLConnection) url.openConnection();                 // create connection
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");                       // specify header
        con.setReadTimeout(5000);
        con.setConnectTimeout(5000);
        return con;
    }


    private String getResponse(HttpURLConnection connection) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));    // create reader object from api response data
        String inputLine;
        StringBuilder content = new StringBuilder();                                              // create object, where to store data from reader
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        return content.toString();
    }

}
