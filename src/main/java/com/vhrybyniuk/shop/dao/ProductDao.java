package com.vhrybyniuk.shop.dao;

import com.vhrybyniuk.shop.entity.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao {
    List<Product> findAll() throws SQLException;

    void add(Product product);

    void update(Product product);

    Product get(int id);

    void delete(int id) throws SQLException;

}
