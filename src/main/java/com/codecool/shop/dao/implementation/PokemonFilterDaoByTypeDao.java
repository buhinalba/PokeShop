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
        int MAX_LIMIT = 20;

        String typeResponse = getTypeResponse(type);

        List<Pokemon> pokemonsOfType = new ArrayList<>();

        if (typeResponse != null) {
            JSONObject TypeResponseJson = (JSONObject) JSONValue.parse(typeResponse);
            JSONArray pokemonsOfTypeJSON = (JSONArray) TypeResponseJson.get("pokemon");

            int size = pokemonsOfTypeJSON.size();
            offset = offset < size-1 ? offset : 0;
            int limit = offset+ MAX_LIMIT < size ? MAX_LIMIT : size-offset;

            PokemonDaoMem pokemonDaoMem = PokemonDaoMem.getInstance();
            for (int i = offset; i < offset + limit; i++) {
                JSONObject poke = (JSONObject) ((JSONObject) pokemonsOfTypeJSON.get(i)).get("pokemon");
                String pokeUrl = poke.get("url").toString();
                Pokemon pokemon = pokemonDaoMem.getPokemonFromUrl(pokeUrl);
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


}
