package com.codecool.shop.mem.dao.test;

import com.codecool.shop.dao.UtilDao;
import com.codecool.shop.dao.implementation.PokemonCategoryDaoMem;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

class PokemonCategoryDaoMemTest {
    private PokemonCategoryDaoMem categoryDaoMem = PokemonCategoryDaoMem.getInstance();

    PokemonCategoryDaoMemTest() throws IOException {
    }

    @Test
    void getAllTypeNames_fromJsonArr_returnsCategoryList() {
        String json = "[" +
                        "{\"name\":\"normal\",\"url\":\"https://pokeapi.co/api/v2/type/1/\"}," +
                        "{\"name\":\"fighting\",\"url\":\"https://pokeapi.co/api/v2/type/2/\"}," +
                        "{\"name\":\"flying\",\"url\":\"https://pokeapi.co/api/v2/type/3/\"}," +
                        "{\"name\":\"poison\",\"url\":\"https://pokeapi.co/api/v2/type/4/\"}]";
        JSONArray jsonResponse = (JSONArray) JSONValue.parse(json);   // create fake json array
        List<String> expected = new ArrayList<>();
        expected.add("normal"); expected.add("fighting"); expected.add("flying"); expected.add("poison");
        assertEquals(expected, categoryDaoMem.getPokemonCategoriesListFromJsonArray(jsonResponse));
    }

    @Test
    void getPokemonCategories_fromUrl_returnsJsonArray() throws IOException {
        String api = "https://pokeapi.co/api/v2/type";
        String json = "[{\"name\":\"normal\",\"url\":\"https:\\/\\/pokeapi.co\\/api\\/v2\\/type\\/1\\/\"},{\"name\":\"fighting\",\"url\":\"https:\\/\\/pokeapi.co\\/api\\/v2\\/type\\/2\\/\"},{\"name\":\"flying\",\"url\":\"https:\\/\\/pokeapi.co\\/api\\/v2\\/type\\/3\\/\"},{\"name\":\"poison\",\"url\":\"https:\\/\\/pokeapi.co\\/api\\/v2\\/type\\/4\\/\"},{\"name\":\"ground\",\"url\":\"https:\\/\\/pokeapi.co\\/api\\/v2\\/type\\/5\\/\"},{\"name\":\"rock\",\"url\":\"https:\\/\\/pokeapi.co\\/api\\/v2\\/type\\/6\\/\"},{\"name\":\"bug\",\"url\":\"https:\\/\\/pokeapi.co\\/api\\/v2\\/type\\/7\\/\"},{\"name\":\"ghost\",\"url\":\"https:\\/\\/pokeapi.co\\/api\\/v2\\/type\\/8\\/\"},{\"name\":\"steel\",\"url\":\"https:\\/\\/pokeapi.co\\/api\\/v2\\/type\\/9\\/\"},{\"name\":\"fire\",\"url\":\"https:\\/\\/pokeapi.co\\/api\\/v2\\/type\\/10\\/\"},{\"name\":\"water\",\"url\":\"https:\\/\\/pokeapi.co\\/api\\/v2\\/type\\/11\\/\"},{\"name\":\"grass\",\"url\":\"https:\\/\\/pokeapi.co\\/api\\/v2\\/type\\/12\\/\"},{\"name\":\"electric\",\"url\":\"https:\\/\\/pokeapi.co\\/api\\/v2\\/type\\/13\\/\"},{\"name\":\"psychic\",\"url\":\"https:\\/\\/pokeapi.co\\/api\\/v2\\/type\\/14\\/\"},{\"name\":\"ice\",\"url\":\"https:\\/\\/pokeapi.co\\/api\\/v2\\/type\\/15\\/\"},{\"name\":\"dragon\",\"url\":\"https:\\/\\/pokeapi.co\\/api\\/v2\\/type\\/16\\/\"},{\"name\":\"dark\",\"url\":\"https:\\/\\/pokeapi.co\\/api\\/v2\\/type\\/17\\/\"},{\"name\":\"fairy\",\"url\":\"https:\\/\\/pokeapi.co\\/api\\/v2\\/type\\/18\\/\"},{\"name\":\"unknown\",\"url\":\"https:\\/\\/pokeapi.co\\/api\\/v2\\/type\\/10001\\/\"},{\"name\":\"shadow\",\"url\":\"https:\\/\\/pokeapi.co\\/api\\/v2\\/type\\/10002\\/\"}]";
        assertEquals((JSONArray) JSONValue.parse(json), categoryDaoMem.getPokemonCategoriesJsonArrayFromUrl(UtilDao.getHttpUrlConnection(api)));
    }

    @Test
    void getAllTypeNames_invalidUrl_throwsIOException() throws IOException {
        // malformed url exception? if url = "not valid"
        // UnknownHostException if url = "https://pokapi.co/api/v2/type"
        HttpURLConnection url = mock(HttpURLConnection.class);
        when(url.getInputStream()).thenThrow(IOException.class);
        assertEquals(IOException.class, categoryDaoMem.getPokemonCategoriesJsonArrayFromUrl(url)); // ?
    }

}
