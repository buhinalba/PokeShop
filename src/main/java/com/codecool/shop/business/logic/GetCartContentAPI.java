package com.codecool.shop.business.logic;

import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.model.Pokemon;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


@WebServlet(urlPatterns = {"/cart-content"}) // query parameters: must have: type name or id, optional: offset and limit as query parameters
public class GetCartContentAPI extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CartDaoMem cartDao = CartDaoMem.getInstance();
        List<Pokemon> smh = cartDao.getAll();

        response.setContentType("application/json");response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println("{\"test json\": 3}");
    }

}
