package com.spring.myfood.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.myfood.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
    @Query("{'name': ?0}")
    public List<Product> findByName(@Param(value = "productName") String productName);
}
