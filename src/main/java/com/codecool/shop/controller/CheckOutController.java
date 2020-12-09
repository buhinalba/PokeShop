package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.UtilDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(urlPatterns = {"/checkout"})
public class CheckOutController extends HttpServlet implements UtilDao {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        CartDaoMem cartDaoMem = CartDaoMem.getInstance();

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());

        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("pokemonMap", cartDaoMem.getAll());
        context.setVariable("totalPrice", cartDaoMem.getTotalPrice());
        context.setVariable("totalAmount", cartDaoMem.getTotalAmount());
        // Todo need total price, total items, each price for each pokemon

        engine.process("product/checkout.html", context, resp.getWriter());
    }
}
