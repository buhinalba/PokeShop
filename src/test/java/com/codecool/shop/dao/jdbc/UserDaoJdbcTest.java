package com.codecool.shop.dao.jdbc;

import com.codecool.shop.model.User;
import org.junit.jupiter.api.Test;

import java.awt.event.ItemEvent;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoJdbcTest {
    UserDaoJdbc testUserDao = new UserDaoJdbc();
    final String FAKE_EMAIL = "test.user@gmail.com";
    final String FAKE_NAME = "test user name";

    @Test
    void add() {
        User testUser = addFakeUserToDatabase();
        User userRes = testUserDao.find(testUser.getEmail());
        assertEquals(testUser.getEmail(), userRes.getEmail());
        assertEquals(testUser.getName(), userRes.getName());

        testUserDao.remove(testUser.getId());
    }


    @Test
    void emailExists_ifEmailExists_returnsTrue() {
        addFakeUserToDatabase();
        assertTrue(testUserDao.emailExists(FAKE_EMAIL));
    }


    @Test
    void emailExists_ifEmailDoesntExist_returnsFalse() {
        assertFalse(testUserDao.emailExists(FAKE_EMAIL));
    }


    @Test
    void remove() {
        User user = addFakeUserToDatabase();
        testUserDao.remove(user.getId());
        assertNull(testUserDao.find(FAKE_EMAIL));
    }

    User addFakeUserToDatabase() {
        User user = new User(FAKE_NAME, FAKE_EMAIL, "not a password");
        testUserDao.add(user);
        return testUserDao.find(FAKE_EMAIL);
    }
}