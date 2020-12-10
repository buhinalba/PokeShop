package com.codecool.shop.controller;

import com.codecool.shop.business.logic.EmailHandler;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.UtilDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;


@WebServlet(urlPatterns = {"/valid-checkout"})
public class ValidCheckOutController extends HttpServlet implements UtilDao {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        EmailHandler emailHandler = new EmailHandler();
        CartDaoMem cartDaoMem = CartDaoMem.getInstance();



    }

}
