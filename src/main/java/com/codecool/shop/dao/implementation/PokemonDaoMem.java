package com.codecool.shop.dao.implementation;


import com.codecool.shop.dao.PokemonDao;
import com.codecool.shop.dao.UtilDao;
import com.codecool.shop.model.Pokemon;
import com.codecool.shop.model.PokemonCategory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PokemonDaoMem implements PokemonDao {

    private List<Pokemon> data = new ArrayList<>();
    private static PokemonDaoMem instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private PokemonDaoMem() {
    }

    public static PokemonDaoMem getInstance() {
        if (instance == null) {
            instance = new PokemonDaoMem();
        }
        return instance;
    }

    public void addAll(List<Pokemon> pokemonList) {
        for (Pokemon pokemon : pokemonList) {
            add(pokemon);
        }
    }

    @Override
    public void add(Pokemon pokemon) {
        pokemon.setId(data.size() + 1);
        data.add(pokemon);
    }

    @Override
    public Pokemon find(int id) {
        return data.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        data.remove(find(id));
    }

    @Override
    public List<Pokemon> getAll() {
        return data;
    }

    @Override
    public List<Pokemon> getBy(PokemonCategory pokemonCategory) {
        return data.stream().filter(t -> t.getProductCategory().equals(pokemonCategory)).collect(Collectors.toList());
    }

    public Pokemon getPokemonFromUrl(String url) throws IOException {
        HttpURLConnection pokeURL = UtilDao.getHttpUrlConnection(url);
        String pokeResponse = UtilDao.getResponse(pokeURL);
        JSONObject pokemonJsonObject = (JSONObject) JSONValue.parse(pokeResponse);

        int pokemonId = Integer.parseInt(pokemonJsonObject.get("id").toString());
        String pokemonName = pokemonJsonObject.get("name").toString();
        int pokemonPrice = Integer.parseInt(pokemonJsonObject.get("base_experience").toString()); // shouldn't money be in int ??
        String pokemonSprite = (String) ((JSONObject) pokemonJsonObject.get("sprites")).get("front_default");
        pokemonSprite = pokemonSprite == null ? "No Image" : pokemonSprite;

        List<String> pokemonCategoryNames = new ArrayList<>();

        JSONArray pokemonCategories = (JSONArray) pokemonJsonObject.get("types");
        for (Object category :pokemonCategories) {
            String pokemonCategoryName = (String) ((JSONObject) ((JSONObject) category).get("type")).get("name");
            pokemonCategoryNames.add(pokemonCategoryName);
        }
        return new Pokemon(pokemonId, pokemonName, pokemonPrice, pokemonCategoryNames, pokemonSprite);
    }
}
