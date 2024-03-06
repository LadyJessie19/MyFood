package com.spring.myfood.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.myfood.model.Product;
import com.spring.myfood.model.Ranking;
import com.spring.myfood.service.RankingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/rankings")
@Tag(name = "Ranking", description = "The Ranking endpoints to search and view products and categories scores")
public class RankingController {

    @Autowired
    private RankingService rankingService;

    @GetMapping("/search")
    @Operation(summary = "Search for products at the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of products", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)) }),
            @ApiResponse(responseCode = "404", description = "Products not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    public ResponseEntity<List<Product>> searchFood(
            @Parameter(description = "The name of the food", required = true) @RequestParam String foodTitle) {

        if (foodTitle == null || foodTitle.isEmpty()) {
            return ResponseEntity.ok().body(Collections.emptyList());
        }

        return ResponseEntity.ok().body(rankingService.searchingFoods(foodTitle));
    }

    @GetMapping("/top-foods")
    @Operation(summary = "List of the 3 top most searched foods")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of rankings", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Ranking.class)) }),
            @ApiResponse(responseCode = "404", description = "The rankings were not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    public ResponseEntity<List<Ranking>> findMostScoredFoods() {
        Pageable pageable = PageRequest.of(0, 3);
        return ResponseEntity.ok().body(rankingService.mostScoredFoods(pageable));
    }

    @GetMapping("/top-categories")
    @Operation(summary = "List of the 3 top most searched categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of rankings", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Ranking.class)) }),
            @ApiResponse(responseCode = "404", description = "The rankings were not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    public ResponseEntity<List<Ranking>> findMostSearchedCategories() {
        Pageable pageable = PageRequest.of(0, 3);
        return ResponseEntity.ok().body(rankingService.findMostSearchedCategories(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find a ranking by id (MongoDB ObjectId)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ranking found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Ranking.class)) }),
            @ApiResponse(responseCode = "404", description = "Ranking not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    public ResponseEntity<Ranking> findRankingById(
            @Parameter(description = "The Ranking id", required = true) @PathVariable String id) {
        return ResponseEntity.ok().body(rankingService.findRankingById(id));
    }
}