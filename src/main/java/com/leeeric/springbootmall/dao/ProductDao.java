package com.leeeric.springbootmall.dao;

import com.leeeric.springbootmall.constant.ProductCategory;
import com.leeeric.springbootmall.dto.ProductRequest;
import com.leeeric.springbootmall.model.Product;

import java.util.List;

public interface ProductDao {
    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProduct(Integer productId);

    List<Product> getProducts(ProductCategory category, String search);
}
