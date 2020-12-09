package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.PokemonDao;
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
    public List<Pokemon> getAll(String urlString) throws IOException {
        HttpURLConnection connection = UtilDao.getHttpUrlConnection(urlString);
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

            int pokemonId = Integer.parseInt(pokemonJsonObject.get("id").toString());
            String pokemonName = (String) pokemonJsonObject.get("name");
            int pokemonPrice = Integer.parseInt(pokemonJsonObject.get("base_experience").toString());
            String pokemonSprite = (String) ((JSONObject) pokemonJsonObject.get("sprites")).get("front_default");
            pokemonSprite = pokemonSprite == null ? "No image" : pokemonSprite;
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

    @Override
    public void addAllPokemonsToPokemonDaoMem(String urlString) throws IOException {
        PokemonDao pokemonDaoMem = PokemonDaoMem.getInstance();
        List<Pokemon> pokemonList = getAll(urlString);
        for (Pokemon pokemon : pokemonList) {
            pokemonDaoMem.add(pokemon);
        }
    }

    @Override
    public String getPreviousPokemons() throws IOException {
        HttpURLConnection connection = UtilDao.getHttpUrlConnection("https://pokeapi.co/api/v2/pokemon/");
        String resultString = UtilDao.getResponse(connection);
        connection.disconnect();

        return (String) ((JSONObject) JSONValue.parse(resultString)).get("next");
    }

    @Override
    public String getNextPokemons() throws IOException {
        HttpURLConnection connection = UtilDao.getHttpUrlConnection("https://pokeapi.co/api/v2/pokemon/");
        String resultString = UtilDao.getResponse(connection);
        connection.disconnect();

        return (String) ((JSONObject) JSONValue.parse(resultString)).get("previous");
    }
}
