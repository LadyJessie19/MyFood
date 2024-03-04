package com.spring.myfood.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.myfood.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
    @Query("{'name': :foodTitle}")
    public List<Product> findByName(@Param("foodTitle") String foodTitle);

    @Query("{'title': { $regex: :foodTitle, $options: 'i' }}")
    List<Product> findByTitleRegexIgnoreCase(@Param("foodTitle") String foodTitle);
}
