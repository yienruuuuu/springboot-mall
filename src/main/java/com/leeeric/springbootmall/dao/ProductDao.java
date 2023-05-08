package com.leeeric.springbootmall.dao;

import com.leeeric.springbootmall.model.Product;

public interface ProductDao {
    Product getProductById(Integer productId);
}
