package com.spring.myfood.mongo;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

import com.spring.myfood.enums.FoodCategoryEnum;
import com.spring.myfood.enums.RankingTypeEnum;
import com.spring.myfood.model.Product;
import com.spring.myfood.model.Ranking;

@Component
public class MyFoodMongo {

    @Autowired
    private MongoTemplate mongoTemplate;

    public Ranking findRankingByTitleAndType(String title, RankingTypeEnum type) {
        Query query = new Query();
        query.addCriteria(Criteria.where("title").is(title)
                .regex(Pattern.compile("^" + title + "$", Pattern.CASE_INSENSITIVE)).and("type").is(type));
        return mongoTemplate.findOne(query, Ranking.class);
    }

    public List<Product> searchFoods(String foodTitle) {
        Query query = new Query();
        Criteria criteria = Criteria.where("name")
                .regex(Pattern.compile(".*" + foodTitle + ".*", Pattern.CASE_INSENSITIVE));
        query.addCriteria(criteria);
        List<Product> products = mongoTemplate.find(query, Product.class);

        return products;
    }

    public List<Ranking> findMostScoredFoods(Pageable pageable) {
        Query query = new Query(Criteria.where("type").is("FOOD"))
                .with(Sort.by(Sort.Order.desc("score")))
                .with(pageable);
        return mongoTemplate.find(query, Ranking.class);
    }

    public List<Ranking> findMostSearchedCategories(Pageable pageable) {
        Query query = new Query(Criteria.where("type").is("CATEGORY"))
                .with(Sort.by(Sort.Order.desc("score")))
                .with(pageable);
        return mongoTemplate.find(query, Ranking.class);
    }

    public Page<Product> findProductsByCategory(FoodCategoryEnum category, Pageable pageable) {
        Query query = new Query();
        query.addCriteria(Criteria.where("category").is(category));
        query.with(pageable);

        // Nova instância de Query apenas para a contagem
        Query countQuery = new Query();
        countQuery.addCriteria(Criteria.where("category").is(category));

        long total = mongoTemplate.count(countQuery, Product.class);

        return PageableExecutionUtils.getPage(
                mongoTemplate.find(query, Product.class),
                pageable,
                () -> total);
    }
}
