package com.spring.myfood.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.myfood.dtos.request.RequestProductDTO;
import com.spring.myfood.dtos.response.ResponseSearchFoodDTO;
import com.spring.myfood.enums.FoodCategoryEnum;
import com.spring.myfood.model.Product;
import com.spring.myfood.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/products")
@Tag(name = "Products", description = "The Products endpoints to create and list products")
public class ProductController {

        @Autowired
        public ProductService productService;

        @PostMapping("register")
        @Operation(summary = "Register a new product")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Product created", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)) }),
                        @ApiResponse(responseCode = "404", description = "Product not created", content = @Content),
                        @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
                        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
        public ResponseEntity<Product> registerProduct(
                        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Product to register", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = RequestProductDTO.class), examples = @ExampleObject(value = "{\n"
                                        +
                                        "  \"name\": \"The product name goes here\",\n" +
                                        "  \"description\": \"The product description goes here\",\n" +
                                        "  \"price\": 10.0,\n" +
                                        "  \"image\": \"An image url\",\n" +
                                        "  \"category\": \"FoodCategoryEnum (PIZZA - HAMBURGER - SANDWICH - PASTA - SALAD - SUSHI - BBQ - DESSERT - VEGETARIAN - VEGAN - FRUIT - BEVERAGE )\"\n"
                                        +
                                        "}"))) @RequestBody(required = true) RequestProductDTO product)
                        throws BadRequestException {

                if (product == null) {
                        return ResponseEntity.badRequest().body(null);
                }

                return ResponseEntity.created(URI.create("/" + product.getName()))
                                .body(productService.registerNewProduct(product));
        }

        @GetMapping("/all")
        @Operation(summary = "List all products")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "List of products", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)) }),
                        @ApiResponse(responseCode = "404", description = "Products not found", content = @Content),
                        @ApiResponse(responseCode = "400", description = "Bad request / Invalid category", content = @Content),
                        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
        })
        public ResponseEntity<Page<Product>> listAllProducts(
                        @Parameter(description = "Page number.") @RequestParam(defaultValue = "0") int page,
                        @Parameter(description = "Number of products per page.") @RequestParam(defaultValue = "5") int size,
                        @Parameter(description = "Optional parameter to filter products by category.") @RequestParam(required = false) FoodCategoryEnum category) {

                Pageable pageable = PageRequest.of(page, size);

                if (category != null && this.isValidCategory(category.toString())) {
                        Page<Product> products = productService.findProductsByCategory(category, pageable);
                        return ResponseEntity.ok().body(products);
                } else {
                        Page<Product> products = productService.findAllProducts(pageable);
                        return ResponseEntity.ok().body(products);
                }
        }

        @GetMapping("/{id}")
        @Operation(summary = "Find a product by id (MongoDB ObjectId)")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Product found", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)) }),
                        @ApiResponse(responseCode = "404", description = "Product not found", content = @Content),
                        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
        public ResponseEntity<ResponseSearchFoodDTO> findProductById(
                        @Parameter(description = "Product id", required = true) @PathVariable String id) {
                return ResponseEntity.ok().body(productService.findProductById(id));
        }

        private boolean isValidCategory(String category) {
                return Arrays.stream(FoodCategoryEnum.values())
                                .anyMatch(enumValue -> enumValue.name().equalsIgnoreCase(category));
        }
}
