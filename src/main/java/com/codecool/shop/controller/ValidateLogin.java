package com.codecool.shop.controller;

import com.codecool.shop.dao.jdbc.UserDaoJdbc;
import com.google.gson.JsonParser;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet(urlPatterns = {"/validate-login"})
public class ValidateLogin extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // gotta parse json
        StringBuffer jb = new StringBuffer();
        String line = null;
        JSONObject jsonObject = new JSONObject();
        try {
            BufferedReader reader = req.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
        } catch (Exception e) { /*report an error*/ }

        try {
            JSONParser parser = new JSONParser();
            jsonObject = (JSONObject) parser.parse(jb.toString());
        } catch (ParseException e) {
            System.out.println("fail");
            // crash and burn
        }

        String email = (String) jsonObject.get("email");
        String password = (String) jsonObject.get("password");
        System.out.println(email);

        UserDaoJdbc userDaoJdbc = new UserDaoJdbc();

        String isValid = userDaoJdbc.validLogin(email, password) ? "true" : "false";

        String response = String.format("{\"success\": %s}", isValid);

        resp.setContentType("application/json"); resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        out.println(response);
    }

}
