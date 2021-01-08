package com.codecool.shop.controller;

import com.codecool.shop.business.logic.SessionController;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.jdbc.UserDaoJdbc;
import com.codecool.shop.model.User;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"/login"})
public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        engine.process("user_account/login.html", context, resp.getWriter());
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String email = req.getParameter("email");
        String pw = req.getParameter("password");

        UserDaoJdbc userDao = new UserDaoJdbc();

        if (userDao.validLogin(email, pw)) {
            System.out.println("valid login");
            SessionController sessionController = SessionController.getInstance();

            HttpSession session = req.getSession();
            String sessionId = session.getId();

            User user = userDao.find(email);
            user.setSessionId(sessionId);

            sessionController.addSession(user);
            resp.sendRedirect(req.getContextPath() + "/");
        } else {
            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }
}
