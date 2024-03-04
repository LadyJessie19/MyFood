package com.spring.myfood.dtos.response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spring.myfood.enums.FoodCategoryEnum;
import com.spring.myfood.enums.RankingTypeEnum;
import com.spring.myfood.model.Product;
import com.spring.myfood.model.Ranking;
import com.spring.myfood.repository.RankingRepository;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class ResponseSearchFoodDTO {
    @Autowired
    private RankingRepository rankingRepository;

    public ResponseSearchFoodDTO(Product product) {
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.image = product.getImage();
        this.category = product.getCategory();
    }

    private String name;
    private String description;
    private int price;
    private String image;
    private FoodCategoryEnum category;
    private int score = 0;

    // @PostConstruct
    // public void searchProductScore() {
    // Ranking foundRanking = rankingRepository.findByTitleAndType(name,
    // RankingTypeEnum.FOOD);

    // if (foundRanking != null) {
    // this.score = foundRanking.getScore();
    // } else {
    // this.score = 0;
    // }
    // }
}
