package com.codecool.shop.business.logic;

import com.codecool.shop.config.DataHandlerConfig;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.PokemonCategoryDao;
import com.codecool.shop.dao.PokemonDao;
import com.codecool.shop.dao.UtilDao;
import com.codecool.shop.dao.implementation.PokemonCategoryDaoMem;
import com.codecool.shop.dao.implementation.PokemonDaoMem;
import com.codecool.shop.dao.implementation.PokemonFilterDaoByTypeDao;
import com.codecool.shop.dao.implementation.PokemonGetAllDao;

import com.codecool.shop.model.Pokemon;
import com.google.gson.Gson;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


@WebServlet(urlPatterns = {"/pokemons/"})
public class PokemonPaginationAPI extends HttpServlet implements UtilDao {

    private PokemonGetAllDao pokemonGetAllDao = new PokemonGetAllDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        int offset = request.getParameter("offset") != null ? Integer.parseInt(request.getParameter("offset")) : 0;

//        String pokemonsJson = pokemonGetAllDao.pokemonPagination(offset); // prev version
        String pokemonsJson = DataHandlerConfig.getPokemonDaoPagination(offset);

        out.println(pokemonsJson);
    }
}