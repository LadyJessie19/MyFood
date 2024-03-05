package com.spring.myfood.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.myfood.dtos.request.RequestProductDTO;
import com.spring.myfood.dtos.response.ResponseSearchFoodDTO;
import com.spring.myfood.model.Product;
import com.spring.myfood.service.ProductService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.net.URI;
import java.util.List;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/products")
@SecurityRequirement(name = "Authorization")
public class ProductController {

    @Autowired
    public ProductService productService;

    @PostMapping("register")
    public ResponseEntity<Product> registerProduct(@RequestBody RequestProductDTO product) throws BadRequestException {

        if (product == null) {
            return ResponseEntity.badRequest().body(null);
        }

        return ResponseEntity.created(URI.create("/" + product.getName()))
                .body(productService.registerNewProduct(product));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> listAllProducts() {
        return ResponseEntity.ok().body(productService.findAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseSearchFoodDTO> findProductById(@PathVariable String id) {
        return ResponseEntity.ok().body(productService.findProductById(id));
    }
}
