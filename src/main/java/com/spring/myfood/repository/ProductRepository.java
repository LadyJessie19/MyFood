package com.spring.myfood.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.spring.myfood.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
}
