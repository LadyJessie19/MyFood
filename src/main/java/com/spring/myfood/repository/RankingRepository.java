package com.spring.myfood.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.spring.myfood.enums.RankingTypeEnum;
import com.spring.myfood.model.Ranking;

public interface RankingRepository extends MongoRepository<Ranking, String> {
    @Query("{ 'title': ?0, 'type': ?1 }")
    Ranking findByTitleAndType(String title, RankingTypeEnum type);

    @Query("{ 'type': 'food' }")
    List<Ranking> findTopFoodsRankingsOrderByScoreDesc(Pageable pageable);

    @Query("{ 'type': 'category' }")
    List<Ranking> findTopCategoriesRankingsOrderByScoreDesc(Pageable pageable);
}
