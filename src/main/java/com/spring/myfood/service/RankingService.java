package com.spring.myfood.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.spring.myfood.enums.FoodCategoryEnum;
import com.spring.myfood.enums.RankingTypeEnum;
import com.spring.myfood.model.Product;
import com.spring.myfood.model.Ranking;
import com.spring.myfood.repository.ProductRepository;
import com.spring.myfood.repository.RankingRepository;

@Service
public class RankingService {

    @Autowired
    public RankingRepository rankingRepository;

    @Autowired
    public ProductRepository productRepository;

    public List<Product> searchingFoods(String foodTitle) {
        String lowerFoodTitle = foodTitle.toLowerCase();

        updateFoodRanking(lowerFoodTitle, RankingTypeEnum.FOOD);

        List<Product> result = searchFoods(lowerFoodTitle);

        return result;
    }

    public void updateFoodRanking(String foodTitle, RankingTypeEnum type) {

        if (foodTitle != null) {

            Ranking existsRanking = rankingRepository.findByTitleAndType(foodTitle, type);

            if (existsRanking != null) {
                updateCategoryRanking(existsRanking.getTitle());
                existsRanking.setScore(existsRanking.getScore() + 1);
                rankingRepository.save(existsRanking);
            } else {
                Ranking newRanking = new Ranking(foodTitle, type, 1);
                updateCategoryRanking(newRanking.getTitle());
                rankingRepository.save(newRanking);
            }
        } else {
            return;
        }
    }

    public void updateCategoryRanking(String foodTitle) {

        List<Product> foundProduct = productRepository.findByTitleRegexIgnoreCase(foodTitle);

        if (foundProduct.isEmpty()) {
            return;
        }

        String category = foundProduct.get(0).getCategory().toString().toUpperCase();

        if (isValidCategory(category)) {
            Ranking existsRanking = rankingRepository.findByTitleAndType(category, RankingTypeEnum.CATEGORY);

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

    public List<Product> searchFoods(String foodTitle) {
        return productRepository.findByTitleRegexIgnoreCase(foodTitle);
    }

    public List<Ranking> mostScoredFoods(Pageable pageable) {
        return rankingRepository.findTopFoodsRankingsOrderByScoreDesc(pageable);
    }

    public Ranking findRankingById(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        return rankingRepository.findById(id).get();
    }

    public List<Ranking> findMostSearchedCategories(Pageable pageable) {
        return rankingRepository.findTopCategoriesRankingsOrderByScoreDesc(pageable);
    }

    // private List<Product> searchFoodsQuery(String foodTitle) {
    // Query query = new Query();
    // query.addCriteria(Criteria.where("name").is(foodTitle));
    // List<Product> products = productRepository.
    // }
}