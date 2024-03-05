package com.spring.myfood.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.spring.myfood.enums.FoodCategoryEnum;
import com.spring.myfood.enums.RankingTypeEnum;
import com.spring.myfood.model.Product;
import com.spring.myfood.model.Ranking;
import com.spring.myfood.mongo.MyFoodMongo;
import com.spring.myfood.repository.ProductRepository;
import com.spring.myfood.repository.RankingRepository;

@Service
public class RankingService {

    @Autowired
    public RankingRepository rankingRepository;

    @Autowired
    public ProductRepository productRepository;

    @Autowired
    private MyFoodMongo myFoodMongo;

    public List<Product> searchingFoods(String foodTitle) {
        List<Product> foundProducts = searchFoods(foodTitle.toLowerCase());

        for (Product product : foundProducts) {
            updateFoodRanking(product, RankingTypeEnum.FOOD);
        }

        return foundProducts;
    }

    public void updateFoodRanking(Product product, RankingTypeEnum type) {

        if (product != null) {

            Ranking existsRanking = myFoodMongo.findRankingByTitleAndType(product.getName(), type);

            if (existsRanking != null) {
                updateCategoryRanking(existsRanking.getTitle());
                existsRanking.setScore(existsRanking.getScore() + 1);
                rankingRepository.save(existsRanking);
            } else {
                Ranking newRanking = new Ranking(product.getName(), type, 1);
                updateCategoryRanking(newRanking.getTitle());
                rankingRepository.save(newRanking);
            }
        } else {
            throw new IllegalArgumentException("Product cannot be null");
        }
    }

    public void updateCategoryRanking(String foodTitle) {

        List<Product> foundProduct = myFoodMongo.searchFoods(foodTitle);

        if (foundProduct.isEmpty()) {
            return;
        }

        String category = foundProduct.get(0).getCategory().toString().toUpperCase();

        if (isValidCategory(category)) {
            Ranking existsRanking = myFoodMongo.findRankingByTitleAndType(category, RankingTypeEnum.CATEGORY);

            if (existsRanking != null) {
                existsRanking.setScore(existsRanking.getScore() + 1);
                rankingRepository.save(existsRanking);
            } else {
                Ranking newRanking = new Ranking(category, RankingTypeEnum.CATEGORY, 1);
                rankingRepository.save(newRanking);
            }
        } else {
            return;
        }
    }

    private boolean isValidCategory(String category) {
        try {
            FoodCategoryEnum.valueOf(category);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private List<Product> searchFoods(String lowerFoodTitle) {
        return myFoodMongo.searchFoods(lowerFoodTitle);
    }

    public Ranking findRankingById(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        Optional<Ranking> foundRanking = rankingRepository.findById(id);

        if (foundRanking.isEmpty()) {
            throw new IllegalArgumentException("Ranking not found");
        }

        return foundRanking.get();
    }

    public List<Ranking> mostScoredFoods(Pageable pageable) {
        return myFoodMongo.findMostScoredFoods(pageable);
    }

    public List<Ranking> findMostSearchedCategories(Pageable pageable) {
        return myFoodMongo.findMostSearchedCategories(pageable);
    }
}