package com.codecool.shop.dao.implementation;


import com.codecool.shop.dao.PokemonCategoryDao;
import com.codecool.shop.dao.UtilDao;
import com.codecool.shop.model.PokemonCategory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class PokemonCategoryDaoMem implements PokemonCategoryDao {

    private List<PokemonCategory> data = new ArrayList<>();
    private static PokemonCategoryDaoMem instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private PokemonCategoryDaoMem() {
    }

    public static PokemonCategoryDaoMem getInstance() throws IOException {
        if (instance == null) {
            instance = new PokemonCategoryDaoMem();
            instance.getAllTypeNames();
        }
        return instance;
    }

    @Override
    public void add(PokemonCategory category) {
        category.setId(data.size() + 1);
        data.add(category);
    }

    @Override
    public PokemonCategory find(int id) {
        return data.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        data.remove(find(id));
    }

    @Override
    public List<PokemonCategory> getAll() { return data; }

    public void getAllTypeNames() throws IOException {
        HttpURLConnection con = UtilDao.getHttpUrlConnection("https://pokeapi.co/api/v2/type");
        String content = UtilDao.getResponse(con);
        con.disconnect();
        JSONObject jsonResponse = (JSONObject) JSONValue.parse(content);
        JSONArray responseTypes = (JSONArray) jsonResponse.get("results");

        List<String> names = new ArrayList<>();

        for (Object pokeType: responseTypes) {
            names.add(((JSONObject) pokeType).get("name").toString());

        }
        addAll(names);
    }

    public void addAll(List<String> names) {
        for(String typeName : names){
            add(new PokemonCategory(typeName));
        }
    }
}
