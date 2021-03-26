package com.codecool.shop.config;

import com.codecool.shop.dao.PokemonCategoryDao;
import com.codecool.shop.dao.PokemonDao;
import com.codecool.shop.dao.implementation.PokemonCategoryDaoMem;
import com.codecool.shop.dao.implementation.PokemonDaoMem;
import com.codecool.shop.dao.implementation.PokemonFilterDaoByTypeDao;
import com.codecool.shop.dao.implementation.PokemonGetAllDao;
import com.codecool.shop.dao.jdbc.PokemonCategoryDaoJdbc;
import com.codecool.shop.dao.jdbc.PokemonDaoJdbc;
import com.codecool.shop.model.Pokemon;
import com.codecool.shop.model.PokemonCategory;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

public class DataHandlerConfig {

    public static PokemonDao getPokemonDao() throws IOException {
        PokemonDao pokemonDao;
        if (DataManager.getDao().equals("memory")) {
            PokemonGetAllDao pokemonGetAllDao = new PokemonGetAllDao();
            String currentPage = "https://pokeapi.co/api/v2/pokemon/?offset=0&limit=20";
            pokemonGetAllDao.addAllPokemonsToPokemonDaoMem(currentPage);

            pokemonDao = PokemonDaoMem.getInstance();
        } else {
            pokemonDao = new PokemonDaoJdbc(DataManager.connectDataBase());
        }
        return pokemonDao;
    }

    public static String getPokemonDaoPagination(int offset) throws IOException {
        String pokemonJson;
        if (DataManager.getDao().equals("memory")) {
            PokemonGetAllDao pokemonGetAllDao = new PokemonGetAllDao();
            pokemonJson = pokemonGetAllDao.pokemonPagination(offset);
        } else {
            List<Pokemon> pokemonList = new PokemonDaoJdbc(DataManager.connectDataBase()).getAll(offset);
            Gson gson = new Gson();
            pokemonJson = gson.toJson(pokemonList);
        }
        return pokemonJson;
    }

    public static String getPokemonDaoFilterByType(String pokemonCategoryName, int offset) throws IOException {
        String filteredPokemonJson;
        Gson gson = new Gson();
        if (DataManager.getDao().equals("memory")) {
            PokemonFilterDaoByTypeDao filterDaoByTypeDao = PokemonFilterDaoByTypeDao.getInstance();
            PokemonDaoMem pokemonDaoMem = PokemonDaoMem.getInstance();
            List<Pokemon> filteredPokemonList = filterDaoByTypeDao.getPokemons(pokemonCategoryName, offset);
            pokemonDaoMem.setData(filteredPokemonList);

            filteredPokemonJson = gson.toJson(filteredPokemonList);
        } else {
            List<Pokemon> filteredPokemonList = new PokemonDaoJdbc(DataManager.connectDataBase()).getBy(pokemonCategoryName,offset);

            filteredPokemonJson = gson.toJson(filteredPokemonList);
        }
        return filteredPokemonJson;
    }

    public static PokemonCategoryDao getPokemonCategoryDao() throws IOException {
        PokemonCategoryDao pokemonCategoryDao;
        if (DataManager.getDao().equals("memory")) {
            pokemonCategoryDao = PokemonCategoryDaoMem.getInstance();
        } else {
            pokemonCategoryDao = new PokemonCategoryDaoJdbc(DataManager.connectDataBase());
        }
        return pokemonCategoryDao;
    }

}
