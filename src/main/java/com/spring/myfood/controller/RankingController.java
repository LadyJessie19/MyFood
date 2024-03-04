package com.spring.myfood.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import com.spring.myfood.model.Product;
import com.spring.myfood.model.Ranking;
import com.spring.myfood.service.RankingService;

@RestController
@RequestMapping("/rankings")
@SecurityRequirement(name = "Authorization")
public class RankingController {

    @Autowired
    private RankingService rankingService;

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchFood(@RequestParam String foodTitle) {

        if (foodTitle == null || foodTitle.isEmpty()) {
            return ResponseEntity.ok().body(Collections.emptyList());
        }

        return ResponseEntity.ok().body(rankingService.searchingFoods(foodTitle));
    }

    @GetMapping("/top-foods")
    public ResponseEntity<List<Ranking>> findMostScoredFoods(
            @PageableDefault(page = 0, size = 3) Pageable pageable) {
        return ResponseEntity.ok().body(rankingService.mostScoredFoods(pageable));
    }

    @GetMapping("/top-categories")
    public ResponseEntity<List<Ranking>> findMostSearchedCategories(
            @PageableDefault(page = 0, size = 3) Pageable pageable) {
        return ResponseEntity.ok().body(rankingService.findMostSearchedCategories(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ranking> findRankingById(@PathVariable String id) {
        return ResponseEntity.ok().body(rankingService.findRankingById(id));
    }
}