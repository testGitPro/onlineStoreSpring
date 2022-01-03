package com.vhrybyniuk.shop.dao.jdbc;

import com.vhrybyniuk.shop.dao.UserDao;
import com.vhrybyniuk.shop.entity.User;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.UUID;

public class JdbcUser implements UserDao {

    ConnectionDB connectionDB = new ConnectionDB();
    private static final String SELECT_BY_LOGIN_SQL = "SELECT password,sole FROM users WHERE login = ? ;";
    private static final String INSERT_NEW_USER_SQL = "INSERT into users(user_name, login, password, sole) VALUES (?, ?, ?, ?);";
    private static final String FIND_EMAIL_SQL = "SELECT login FROM users WHERE login = ? ;";

    @Override
    public boolean isUserExistsInDB(String email, String password) throws SQLException {

        try (Connection connection = connectionDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_LOGIN_SQL);) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String sole = resultSet.getString("sole");
                String hash = DigestUtils.md5Hex(sole + password);
                if (Objects.equals(hash, resultSet.getString("password"))) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    @Override
    public boolean isEmailExistsInDB(String email) throws SQLException {
        try (Connection connection = connectionDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_EMAIL_SQL)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {

                return true;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    @Override
    public void addUser(User user) throws SQLException {
        String sole = UUID.randomUUID().toString();
        user.setPassword(DigestUtils.md5Hex(sole + user.getPassword()));

        try (Connection connection = connectionDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEW_USER_SQL);) {
            preparedStatement.setString(1, user.getUser_name());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, sole);

            preparedStatement.executeUpdate();


        }
    }


}
