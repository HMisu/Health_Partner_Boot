package com.bit.healthpartnerboot.service;

import com.bit.healthpartnerboot.dto.FoodDTO;
import com.bit.healthpartnerboot.dto.RecommendFoodDTO;
import com.bit.healthpartnerboot.entity.Member;

import java.util.List;

public interface FoodService {
    FoodDTO getTodoFood(String foodCode);

    List<FoodDTO> searchFood(String keyword);

    RecommendFoodDTO getRecommendedFoods(Member member);
}
