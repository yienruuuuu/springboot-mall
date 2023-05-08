package com.leeeric.springbootmall.service;

import com.leeeric.springbootmall.dto.ProductRequest;
import com.leeeric.springbootmall.model.Product;

public interface ProductService {
    Product getProductById(Integer productId);
    Integer createProduct(ProductRequest productRequest);
    void updateProduct(Integer productId,ProductRequest productRequest);
}
