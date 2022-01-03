package com.vhrybyniuk.shop.service;

import com.vhrybyniuk.shop.dao.UserDao;
import com.vhrybyniuk.shop.entity.User;

import java.sql.SQLException;

public class UserService {
    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean isUserExistsInDB(String email, String password) throws SQLException {
        return userDao.isUserExistsInDB(email, password);
    }

    public boolean isEmailExistsInDB(String email) throws SQLException {
        return userDao.isEmailExistsInDB(email);
    }


    public void addUser(User user) throws SQLException {
        userDao.addUser(user);
    }
}
