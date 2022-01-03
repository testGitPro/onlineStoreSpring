package com.vhrybyniuk.shop.dao;

import com.vhrybyniuk.shop.entity.User;

import java.sql.SQLException;

public interface UserDao {
    boolean isUserExistsInDB(String email, String password) throws SQLException;

    boolean isEmailExistsInDB(String email) throws SQLException;

    void addUser(User user) throws SQLException;


}
