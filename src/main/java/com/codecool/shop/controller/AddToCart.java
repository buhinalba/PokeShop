package com.codecool.shop.controller;


import com.codecool.shop.dao.implementation.PokemonDaoMem;
import com.codecool.shop.model.Pokemon;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/add-to-cart"})
public class AddToCart extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int pokemonId = Integer.parseInt(request.getParameter("pokemon-id"));
        PokemonDaoMem pokemonDaoMem = PokemonDaoMem.getInstance();

        Pokemon pokemon = pokemonDaoMem.getPokemonById(pokemonId);



    }
}
