package com.codecool.shop.business.logic;

import com.codecool.shop.model.User;

import java.util.ArrayList;
import java.util.List;

public class SessionController {
    private static SessionController instance;
    private List<User> activeCustomers = new ArrayList<>();

    public static SessionController getInstance() {
        if (instance == null) {
            instance = new SessionController();
        }
        return instance;
    }


    public void addSession(User user) {
        activeCustomers.add(user);
    }

    public User getUser(String sessionId) {
        for(User user: activeCustomers) {
            if (user.getSessionId().equals(sessionId)) {
                return user;
            }
        }
        return null;
    }
}
