package com.spring.myfood.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.spring.myfood.dtos.response.ResponseSearchFoodDTO;
import com.spring.myfood.model.Product;
import com.spring.myfood.model.Ranking;
import com.spring.myfood.repository.RankingRepository;

@Service
public class RankingService {

    @Autowired
    public RankingRepository rankingRepository;

    public List<ResponseSearchFoodDTO> searchingFoods(String searchedFood) {

        return new ArrayList<>();
    }

    public List<Ranking> mostScoredFoods(Pageable pageable) {

        return new ArrayList<>();
    }

    public Ranking findRankingById(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        return rankingRepository.findById(id).get();
    }
}