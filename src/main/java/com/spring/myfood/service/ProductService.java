package com.spring.myfood.service;

import java.util.List;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.spring.myfood.dtos.request.RequestProductDTO;
import com.spring.myfood.dtos.response.ResponseSearchFoodDTO;
import com.spring.myfood.enums.FoodCategoryEnum;
import com.spring.myfood.enums.RankingTypeEnum;
import com.spring.myfood.model.Product;
import com.spring.myfood.model.Ranking;
import com.spring.myfood.mongo.MyFoodMongo;
import com.spring.myfood.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    public ProductRepository productRepository;

    @Autowired
    private MyFoodMongo myFoodMongo;

    public Product registerNewProduct(RequestProductDTO product) throws BadRequestException {

        String upperCategory = product.getCategory().toUpperCase();

        try {
            FoodCategoryEnum.valueOf(upperCategory);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Category not found");
        }

        FoodCategoryEnum category = FoodCategoryEnum.valueOf(upperCategory);

        if ((product.getName() == null) && (product.getDescription() == null) && (product.getPrice() == 0)
                && (product.getImage() == null)) {
            throw new BadRequestException("All fields must be filled");
        }

        Product newProduct = new Product();
        newProduct.setName(product.getName());
        newProduct.setDescription(product.getDescription());
        newProduct.setPrice(product.getPrice());
        newProduct.setImage(product.getImage());
        newProduct.setCategory(category);

        return productRepository.save(newProduct);
    }

    public Page<Product> findAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Page<Product> findProductsByCategory(FoodCategoryEnum category, Pageable pageable) {
        return myFoodMongo.findProductsByCategory(category, pageable);
    }

    public ResponseSearchFoodDTO findProductById(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        Product product = productRepository.findById(id).get();

        if (product == null) {
            throw new IllegalArgumentException("Product not found");
        }

        ResponseSearchFoodDTO response = new ResponseSearchFoodDTO();
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setImage(product.getImage());
        response.setCategory(product.getCategory());

        Ranking foundRanking = myFoodMongo.findRankingByTitleAndType(product.getName(), RankingTypeEnum.FOOD);

        if (foundRanking != null) {
            response.setScore(foundRanking.getScore());
        } else {
            response.setScore(0);
        }

        return response;
    }
}
