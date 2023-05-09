package com.leeeric.springbootmall.service;

import com.leeeric.springbootmall.dao.ProductQueryParams;
import com.leeeric.springbootmall.dto.ProductRequest;
import com.leeeric.springbootmall.model.Product;

import java.util.List;

public interface ProductService {
    Product getProductById(Integer productId);
    Integer createProduct(ProductRequest productRequest);
    void updateProduct(Integer productId,ProductRequest productRequest);
    void deleteProduct(Integer productId);
    List<Product> getProducts(ProductQueryParams productQueryParams);
}
