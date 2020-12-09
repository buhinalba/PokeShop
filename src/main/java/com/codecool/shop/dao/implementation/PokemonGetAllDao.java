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
        PokemonDaoMem pokemonDaoMem = PokemonDaoMem.getInstance();
        List<Pokemon> pokemonList = new ArrayList<>();

        JSONObject jsonResponse = (JSONObject) JSONValue.parse(resultString);
        JSONArray jsonArray = (JSONArray) jsonResponse.get("results");

        for (Object poki : jsonArray) {
            var pokemonTemp = pokemonDaoMem.getPokemonFromUrl(((JSONObject) poki).get("url").toString());
            pokemonList.add(pokemonTemp);
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
