package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.PokemonGetAllDaoInt;
import com.codecool.shop.dao.UtilDao;
import com.codecool.shop.model.Pokemon;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class PokemonGetAllDao implements PokemonGetAllDaoInt {

    @Override
    public List<Pokemon> getAll() throws IOException {
        HttpURLConnection connection = UtilDao.getHttpUrlConnection("https://pokeapi.co/api/v2/pokemon/?offset=0&limit=20");
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

            Integer pokemonId = Integer.parseInt(pokemonJsonObject.get("id").toString());
            String pokemonName = (String) pokemonJsonObject.get("name");
            Long pokemonPrice = (Long) pokemonJsonObject.get("base_experience");
            String pokemonSprite = (String) ((JSONObject) pokemonJsonObject.get("sprites")).get("front_default");

            JSONArray pokemonCategories = (JSONArray) pokemonJsonObject.get("types");
            List<String> pokemonCategoryNames = new ArrayList<>();
            for (Object category : pokemonCategories) {
                String pokemonCategoryName = (String) ((JSONObject) ((JSONObject) category).get("type")).get("name");
                pokemonCategoryNames.add(pokemonCategoryName);
            }

            pokemonList.add(new Pokemon(pokemonId, pokemonName, pokemonPrice, pokemonCategoryNames, pokemonSprite));
        }
        return pokemonList;
    }
}
