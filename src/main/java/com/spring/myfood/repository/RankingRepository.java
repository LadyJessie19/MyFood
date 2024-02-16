package com.spring.myfood.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.spring.myfood.model.Ranking;

public interface RankingRepository extends MongoRepository<Ranking, Long> {
}
