package com.vhrybyniuk.shop.service;

import com.vhrybyniuk.shop.dao.ProductDao;
import com.vhrybyniuk.shop.entity.Product;

import java.sql.SQLException;
import java.util.List;

public class ProductService {
    private ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<Product> findAll() throws SQLException {
        List<Product> products = productDao.findAll();
        return products;
    }

    public void add(Product product) {
        productDao.add(product);
    }


    public void update(Product product) {
        productDao.update(product);

    }

    public Product get(int id) {
        return productDao.get(id);
    }

    public void delete(int id) throws SQLException {
        productDao.delete(id);
    }
}

