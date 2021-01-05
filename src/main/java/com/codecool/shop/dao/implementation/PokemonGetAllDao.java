package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.PokemonDao;
import com.codecool.shop.dao.PokemonGetAllDaoInt;
import com.codecool.shop.dao.UtilDao;
import com.codecool.shop.model.Pokemon;
import com.google.gson.Gson;
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

       var jsonArray = getJSONArrayFromUrlConnectionString(resultString);

        return getPokemonsFromJSON(jsonArray);
    }

    public JSONArray getJSONArrayFromUrlConnectionString(String resultString){
        JSONObject jsonResponse = (JSONObject) JSONValue.parse(resultString);
        JSONArray jsonArray = (JSONArray) jsonResponse.get("results");
        return jsonArray;
    }

    public List<Pokemon> getPokemonsFromJSON(JSONArray jsonArray) throws IOException {
        /**
         * iterates trough JSONArray and add elements to PokemonDaoMem which stores them during runtime
         */
        PokemonDaoMem pokemonDaoMem = PokemonDaoMem.getInstance();
        pokemonDaoMem.clearMem();
        List<Pokemon> pokemonList = new ArrayList<>();

        for (Object poki : jsonArray) {
//            ((JSONObject) poki).get("url").toString() -- changes for testing
            JSONObject pokemonJsonObject = pokemonDaoMem.getPokemonJsonObjectFromUrl(((JSONObject) poki).get("url").toString());
            var pokemonTemp = pokemonDaoMem.getPokemonFromJsonObject(pokemonJsonObject);
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
    public String pokemonPagination(int offset) throws IOException {
        HttpURLConnection connection = UtilDao.getHttpUrlConnection("https://pokeapi.co/api/v2/pokemon/?offset="+offset);
        String resultString = UtilDao.getResponse(connection);
        connection.disconnect();

        var jsonArray = getJSONArrayFromUrlConnectionString(resultString);
        List<Pokemon> pokemons = getPokemonsFromJSON(jsonArray);
        Gson gson = new Gson();
        String pokemonsJson = gson.toJson(pokemons);

        return pokemonsJson;
    }
}
