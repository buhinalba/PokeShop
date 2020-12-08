package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.PokemonFilterDao;
import com.codecool.shop.dao.UtilDao;
import com.codecool.shop.model.Pokemon;
import com.codecool.shop.model.PokemonCategory;
import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class PokemonFilterDaoByTypeDao implements PokemonFilterDao {
    static PokemonFilterDaoByTypeDao instance;

    @Override
    public List<Pokemon> getPokemons() {
        return null;
    }


    public List<Pokemon> getPokemons(String type, int offset) throws IOException {

        String typeResponse = getTypeResponse(type);

        List<Pokemon> pokemonsOfType = new ArrayList<>();

        if (typeResponse != null) {
            JSONObject TypeResponseJson = (JSONObject) JSONValue.parse(typeResponse);
            JSONArray pokemonsOfTypeJSON = (JSONArray) TypeResponseJson.get("pokemon");

            int size = pokemonsOfTypeJSON.size();
            offset = offset < size-1 ? offset : 0;
            int limit = offset+20 < size ? 20 : size-offset;
            for (int i = offset; i < offset + limit; i++) {
                JSONObject poke = (JSONObject) ((JSONObject) pokemonsOfTypeJSON.get(i)).get("pokemon");
                String pokeUrl = poke.get("url").toString();
                Pokemon pokemon = getPokemonFromUrl(pokeUrl);
                pokemonsOfType.add(pokemon);
            }
        }
        return pokemonsOfType;
    }

    public String getTypeResponse(String type) throws IOException {
        HttpURLConnection con = UtilDao.getHttpUrlConnection("https://pokeapi.co/api/v2/type");
        String content = UtilDao.getResponse(con);
        con.disconnect();
        JSONObject jsonResponse = (JSONObject) JSONValue.parse(content);
        JSONArray responseTypes = (JSONArray) jsonResponse.get("results");
        String filteredTypeResponse = null;

        for (Object pokeType: responseTypes) {
            if (((JSONObject) pokeType).get("name").equals(type)) {
                HttpURLConnection conType = UtilDao.getHttpUrlConnection(((JSONObject) pokeType).get("url").toString());
                filteredTypeResponse = UtilDao.getResponse(conType);
                conType.disconnect();
                break;
            }
        }
        return filteredTypeResponse;
    }

    @Override
    public List<String> getAllTypes() {
        return null;
    }

    public static PokemonFilterDaoByTypeDao getInstance() {
        if (instance == null) {
            instance = new PokemonFilterDaoByTypeDao();
        }
        return instance;
    }

    public Pokemon getPokemonFromUrl(String url) throws IOException {
        HttpURLConnection pokeURL = UtilDao.getHttpUrlConnection(url);
        String pokeResponse = UtilDao.getResponse(pokeURL);
        JSONObject pokemonJsonObject = (JSONObject) JSONValue.parse(pokeResponse);

        int pokemonId = Integer.parseInt(pokemonJsonObject.get("id").toString());
        String pokemonName = pokemonJsonObject.get("name").toString();
        float pokemonPrice = Float.parseFloat(pokemonJsonObject.get("base_experience").toString()); // shouldn't money be in int ??
        String pokemonSprite = (String) ((JSONObject) pokemonJsonObject.get("sprites")).get("front_default");
        List<String> pokemonCategoryNames = new ArrayList<>();

        JSONArray pokemonCategories = (JSONArray) pokemonJsonObject.get("types");
        for (Object category :pokemonCategories) {
            String pokemonCategoryName = (String) ((JSONObject) ((JSONObject) category).get("type")).get("name");
            pokemonCategoryNames.add(pokemonCategoryName);
        }
        return new Pokemon(pokemonId, pokemonName, pokemonPrice, pokemonCategoryNames, pokemonSprite);
    }
}
