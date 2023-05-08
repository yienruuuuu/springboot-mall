package com.leeeric.springbootmall.service.impl;

import com.leeeric.springbootmall.dao.ProductDao;
import com.leeeric.springbootmall.dto.ProductRequest;
import com.leeeric.springbootmall.model.Product;
import com.leeeric.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public Product getProductById(Integer productId) {
        return productDao.getProductById(productId);
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        return productDao.createProduct(productRequest);
    }
}
