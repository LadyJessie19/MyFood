package com.spring.myfood.service;

import java.util.List;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.myfood.dtos.request.RequestProductDTO;
import com.spring.myfood.enums.FoodCategoryEnum;
import com.spring.myfood.model.Product;
import com.spring.myfood.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    public ProductRepository productRepository;

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

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Product findProductById(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        return productRepository.findById(id).get();
    }
}
