package com.leeeric.springbootmall.controller;

import com.leeeric.springbootmall.constant.ProductCategory;
import com.leeeric.springbootmall.dao.ProductQueryParams;
import com.leeeric.springbootmall.dto.ProductRequest;
import com.leeeric.springbootmall.model.Product;
import com.leeeric.springbootmall.service.ProductService;
import com.leeeric.springbootmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Validated//加上此註解，@Max、@Min才會生效
@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/product/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId) {
        Product product = productService.getProductById(productId);
        if (product != null) {
            return ResponseEntity.status(HttpStatus.OK).body(product);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();//build=Build the response entity with no body.
        }
    }

    @PostMapping("/product")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest) {
        Integer productId = productService.createProduct(productRequest);
        Product product = productService.getProductById(productId);

        if (product != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(product);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();//build=Build the response entity with no body.
        }
    }

    @PutMapping("/product/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId, @RequestBody @Valid ProductRequest productRequest) {
        //檢查商品原來是否存在，不存在的話就回傳404
        Product productCheck = productService.getProductById(productId);
        if (productCheck == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        //若存在，則進入update方法
        productService.updateProduct(productId, productRequest);
        Product product = productService.getProductById(productId);
        if (product != null) {
            return ResponseEntity.status(HttpStatus.OK).body(product);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/products")
    public ResponseEntity<Page<Product>> getProducts(
            //查詢條件
            @RequestParam(required = false) ProductCategory category,
            @RequestParam(required = false) String search,
            //排序
            @RequestParam(defaultValue = "created_date") String orderBy,
            @RequestParam(defaultValue = "desc") String sort,
            //分頁(limit 取得幾筆數字 state 跳過多少筆數字(offset參數))
            @RequestParam(defaultValue = "5") @Max(1000) Integer limit,
            @RequestParam(defaultValue = "0") @Min(0) Integer offSet
    ) {
        ProductQueryParams productQueryParams = new ProductQueryParams();
        productQueryParams.setCategory(category);
        productQueryParams.setSearch(search);
        productQueryParams.setOrderBy(orderBy);
        productQueryParams.setSort(sort);
        productQueryParams.setLimit(limit);
        productQueryParams.setOffset(offSet);

        //取得productList
        List<Product> productList = productService.getProducts(productQueryParams);

        //取得product總比數
        Integer total = productService.countProduct(productQueryParams);
        //分頁
        Page<Product> productPage = new Page<>();
        productPage.setOffSet(offSet);
        productPage.setLimit(limit);
        productPage.setResults(productList);
        productPage.setTotal(total);

        return ResponseEntity.status(HttpStatus.OK).body(productPage);
    }


}
