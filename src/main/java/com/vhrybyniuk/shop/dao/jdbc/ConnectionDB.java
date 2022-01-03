package com.vhrybyniuk.shop.dao.jdbc;

import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    protected java.sql.Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5433/store", "postgres", "1111");

    }
}
