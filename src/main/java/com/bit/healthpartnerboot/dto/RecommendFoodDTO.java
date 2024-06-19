package com.bit.healthpartnerboot.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RecommendFoodDTO {
    private List<FoodDTO> foodList;
    private double remainingCalories;
    private double remainingProtein;
    private double remainingCarbohydrates;
    private double remainingFat;
}
