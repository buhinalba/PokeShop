package com.codecool.shop.business.logic;

import com.codecool.shop.dao.implementation.CartDaoMem;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {"/edit/increase"})
public class IncreaseCartContent extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int pokemonId = Integer.parseInt(req.getParameter("pokemon-id"));

        CartDaoMem cartDaoMem = CartDaoMem.getInstance();
        cartDaoMem.increasePokemonCount(pokemonId);

        int count = cartDaoMem.getPokemonCount(pokemonId);

        PrintWriter out = resp.getWriter();

        JSONObject responseJson = new JSONObject();
        responseJson.put("count", count);
        responseJson.put("pokemonId", pokemonId);

        String response = responseJson.toJSONString();
        out.println(response);
    }
}
