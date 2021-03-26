package com.codecool.shop.controller;

import com.codecool.shop.dao.UserDao;
import com.codecool.shop.dao.jdbc.UserDaoJdbc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(urlPatterns = {"/registration-validation"})
public class ValidateRegistration extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(ValidateRegistration.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json"); resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        String email = req.getParameter("email");
        UserDao userDao = new UserDaoJdbc();
        String validationIsSuccessful = userDao.emailExists(email) ? "false" : "true";

        String response = String.format("{\"success\": %s}", validationIsSuccessful);

        logger.info("Response: " + response);
        out.println(response);
    }
}
