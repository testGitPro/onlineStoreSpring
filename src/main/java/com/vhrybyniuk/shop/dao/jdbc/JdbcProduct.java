package com.vhrybyniuk.shop.dao.jdbc;

import com.vhrybyniuk.shop.dao.ProductDao;
import com.vhrybyniuk.shop.dao.jdbc.mapper.ProductRowMapper;
import com.vhrybyniuk.shop.entity.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcProduct implements ProductDao {
    private static final String FIND_ALL_SQL = "SELECT id, name, price,created FROM Product";
    private static final String INSERT_SQL = "INSERT into product(name, price) VALUES (?, ?);";
    private static final String UPDATE_SQL = "UPDATE product SET name=?, price=? WHERE id=?;";
    private static final String SELECT_BY_ID_SQL = "SELECT id, name, price, created FROM product WHERE id=?;";
    private static final String DELETE_BY_ID_SQL = "DELETE FROM product WHERE id=?;";

    private static final ProductRowMapper PRODUCT_ROW_MAPPER = new ProductRowMapper();

    ConnectionDB connectionDB = new ConnectionDB();

    @Override
    public List<Product> findAll() {
        try (Connection connection = connectionDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                Product product = PRODUCT_ROW_MAPPER.mapRow(resultSet);
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public void add(Product product) {
        try {
            Connection connection = connectionDB.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void update(Product product) {
        try {
            Connection connection = connectionDB.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setLong(3, product.getId());


            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Product get(int id) {
        Product product = null;

        try {
            Connection connection = connectionDB.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID_SQL);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            product = PRODUCT_ROW_MAPPER.mapRow(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return product;
    }

    @Override
    public void delete(int id) {
        try {

            Connection connection = connectionDB.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID_SQL);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
