package com.codecool.shop.controller;

import com.codecool.shop.dao.jdbc.UserDaoJdbc;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

@WebServlet(urlPatterns = {"/validate-login"})
public class ValidateLogin extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        UserDaoJdbc userDaoJdbc = new UserDaoJdbc();

        boolean isValid = userDaoJdbc.validLogin(email, password);

        String response = String.format("{\"success\": %s}", isValid);

        resp.setContentType("application/json"); resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        out.println(response);
    }

}
