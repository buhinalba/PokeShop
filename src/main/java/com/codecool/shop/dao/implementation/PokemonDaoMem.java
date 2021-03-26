package com.codecool.shop.dao.implementation;


import com.codecool.shop.dao.PokemonDao;
import com.codecool.shop.dao.UtilDao;
import com.codecool.shop.model.Pokemon;
import com.codecool.shop.model.PokemonCategory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class PokemonDaoMem implements PokemonDao {
    private static final Logger logger = LoggerFactory.getLogger(PokemonDaoMem.class);
    private List<Pokemon> data = new ArrayList<>();
    private static PokemonDaoMem instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private PokemonDaoMem() {
    }

    public void setData(List<Pokemon> pokemons) {
        data = pokemons;
    }


    public static PokemonDaoMem getInstance() {
        if (instance == null) {
            instance = new PokemonDaoMem();
        }
        return instance;
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
    public List<Pokemon> getAll(int offset) {
        return data;
    }

    @Override
    public List<Pokemon> getAll() {
        return data;
    }

    @Override
    public List<Pokemon> getBy(PokemonCategory pokemonCategory) {
        return data.stream().filter(t -> t.getPokemonCategory().contains(pokemonCategory.getCategory())).collect(Collectors.toList());
    }

    @Override
    public List<Pokemon> getBy(String pokemonCategoryName, int offset) {
        return null;
    }

    public JSONObject getPokemonJsonObjectFromUrl(String url) throws IOException {
        HttpURLConnection pokeURL = UtilDao.getHttpUrlConnection(url);
        String pokeResponse = UtilDao.getResponse(pokeURL);
        return (JSONObject) JSONValue.parse(pokeResponse);
    }

    public Pokemon getPokemonFromJsonObject(JSONObject pokemonJsonObject) {
        int pokemonId = Integer.parseInt(pokemonJsonObject.get("id").toString());
        String pokemonName = pokemonJsonObject.get("name").toString();
        int pokemonPrice = Integer.parseInt(pokemonJsonObject.get("base_experience").toString());
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

    public void clearMem(){
        data.clear();
    }


    public Pokemon getPokemonById(int id) {
        for(Pokemon pokemon: data) {
            if (pokemon.getId() == id) {
                logger.info("Pokemon found with id: " + id);
                return pokemon;
            }
        }
        logger.warn("No such pokemon with id: " + id);
        throw new NoSuchElementException("Pokemon with id " + id + " not found");
    }
}
