package com.bit.healthpartnerboot.dto;

import com.bit.healthpartnerboot.entity.Todo;
import com.bit.healthpartnerboot.entity.TodoFood;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class TodoFoodDTO {
    private Integer seq;
    private String mealType;
    private String foodCode;
    private String name;
    private String energy;
    private String protein;
    private String fat;
    private String carbohydrates;
    // private FoodDTO food;

    public TodoFood toEntity(Todo todo) {
        return TodoFood.builder()
                .seq(this.seq)
                .todo(todo)
                .mealType(MealType.valueOf(this.mealType))
                .foodCode(foodCode)
                .build();
    }
}
