package com.bit.healthpartnerboot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "TB_MAIN_FOOD_INGREDIENTS")
@AllArgsConstructor
@Builder
@IdClass(MainFoodIngredientsId.class)
public class MainFoodIngredients {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "main_food_code")
    private MainFood mainFood;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_idnt_code")
    private FoodIdnt foodIdnt;
}
