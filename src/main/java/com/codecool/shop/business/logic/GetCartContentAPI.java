package com.codecool.shop.business.logic;

import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.model.Pokemon;
import com.google.gson.Gson;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@WebServlet(urlPatterns = {"/cart-content"}) // query parameters: must have: type name or id, optional: offset and limit as query parameters
public class GetCartContentAPI extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CartDaoMem cartDao = CartDaoMem.getInstance();
        HashMap<Pokemon, Integer> cartContent = cartDao.getAll();
        Gson gson = new Gson();

        List<JSONObject> cartList = new ArrayList<>();

        for (Map.Entry<Pokemon, Integer> entry: cartContent.entrySet()) {
            JSONObject pokemon = new JSONObject();

            JSONObject pokemonProperties = (JSONObject) JSONValue.parse(gson.toJson(entry.getKey()));
            pokemon.put("pokemon", pokemonProperties);
            pokemon.put("count", entry.getValue());
            cartList.add(pokemon);
        }

        String cartContentJson = gson.toJson(cartList);

        String cartInfo = String.format(
                "{\"totalPrice\": %d, " +
                "\"cartContent\": %s}", cartDao.getTotalPrice(), cartContentJson        // todo replace 100 with cartDao.getTotalPrice()
        );

        PrintWriter out = response.getWriter();
        out.println(cartInfo);
    }
}
