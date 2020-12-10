package com.codecool.shop.business.logic;

import com.codecool.shop.dao.implementation.CartDaoMem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {"/edit/delete"})
public class DeleteCartContent extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pokemonId = req.getParameter("pokemon-id");

        CartDaoMem cartDaoMem = CartDaoMem.getInstance();
        cartDaoMem.deletePokemon(Integer.parseInt(pokemonId));

        PrintWriter out = resp.getWriter();

        out.println("{\"pokemonId\": " + pokemonId + "}");
    }
}
