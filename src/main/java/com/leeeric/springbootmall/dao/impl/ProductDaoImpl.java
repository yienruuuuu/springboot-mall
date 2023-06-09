package com.leeeric.springbootmall.dao.impl;

import com.leeeric.springbootmall.RowMapper.ProductRowMapper;
import com.leeeric.springbootmall.dao.ProductDao;
import com.leeeric.springbootmall.dao.ProductQueryParams;
import com.leeeric.springbootmall.dto.ProductRequest;
import com.leeeric.springbootmall.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductDaoImpl implements ProductDao {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Product getProductById(Integer productId) {
        String sql = "SELECT product_id ,product_name, category, image_url, price, stock, description, created_date," +
                "last_modified_date FROM product WHERE product_id = :productId";
        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);
        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());
        if (productList.size() > 0) {
            return productList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        String sql = "INSERT INTO product (" +
                "product_name," +
                "category," +
                "image_url," +
                "price," +
                "stock," +
                "description," +
                "created_date," +
                "last_modified_date)" +
                "VALUES(" +
                ":productName," +
                ":category," +
                ":imageUrl," +
                ":price," +
                ":stock," +
                ":description," +
                ":createdDate," +
                ":lastmodifiedDate)";
        Map<String, Object> map = new HashMap<>();
        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().toString());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());

        Date date = new Date();
        map.put("createdDate", date);
        map.put("lastmodifiedDate", date);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
        int productId = keyHolder.getKey().intValue();//ID是INT類型，可以用intValue()取得id
        return productId;
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
        String sql = "UPDATE  product SET " +
                "product_name = :productName," +
                "category = :category," +
                "image_url = :imageUrl," +
                "price = :price," +
                "stock = :stock," +
                "description = :description," +
                "last_modified_date = :lastModifiedDate " +
                "WHERE " +
                "product_id= :productId";
        Map<String, Object> map = new HashMap<>();
        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().toString());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());

        map.put("lastModifiedDate", new Date());
        map.put("productId", productId);
        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void deleteProduct(Integer productId) {
        String sql = "DELETE FROM product WHERE product_id= :productid";
        Map<String, Object> map = new HashMap<>();
        map.put("productid", productId);
        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public List<Product> getProducts(ProductQueryParams productQueryParams) {
        String sql = "SELECT product_id, product_name, category, image_url, price, stock, description, created_date, last_modified_date FROM product WHERE 1=1";
        Map<String, Object> map = new HashMap<>();
        //查詢條件
        sql = addFilterySql(sql, map, productQueryParams);
        //實作排序功能sql
        sql = sql + " ORDER BY " + productQueryParams.getOrderBy() + " " + productQueryParams.getSort();
        //實作分頁用sql，sql前記得要有空白
        sql = sql + " LIMIT  :limit OFFSET  :offset";
        map.put("limit", productQueryParams.getLimit());
        map.put("offset", productQueryParams.getOffset());

        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());
        return productList;
    }

    @Override
    public Integer countProduct(ProductQueryParams productQueryParams) {
        String sql = "SELECT count(*) FROM product WHERE 1=1 ";
        Map<String, Object> map = new HashMap<>();
        //查詢條件
        sql = addFilterySql(sql, map, productQueryParams);

        Integer total = namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);
        return total;
    }

    /**
     * 進行sql拼接(拼接where條件category及product_name)
     * @param sql
     * @param map
     * @param productQueryParams
     * @return String sql
     */
    private String addFilterySql(String sql, Map<String, Object> map, ProductQueryParams productQueryParams) {
        if (productQueryParams.getCategory() != null) {
            sql = sql + " AND category=:category";
            map.put("category", productQueryParams.getCategory().name());
        }
        if (productQueryParams.getSearch() != null) {
            sql = sql + " AND product_name LIKE :product_name";
            map.put("product_name", "%" + productQueryParams.getSearch() + "%");
        }
        return sql;
    }
}

