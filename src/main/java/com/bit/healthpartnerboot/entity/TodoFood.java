package com.bit.healthpartnerboot.entity;

import com.bit.healthpartnerboot.converter.MealTypeConverter;
import com.bit.healthpartnerboot.dto.FoodDTO;
import com.bit.healthpartnerboot.dto.MealType;
import com.bit.healthpartnerboot.dto.TodoFoodDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "TB_TODO_FOOD")
public class TodoFood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_food_seq")
    private Integer seq;

    @ManyToOne
    @JoinColumn(name = "todo_seq")
    private Todo todo;

    @Column(nullable = false)
    @Convert(converter = MealTypeConverter.class)
    private MealType mealType;

    private String foodCode;

    public TodoFoodDTO toDTO(FoodDTO foodDTO) {
        return TodoFoodDTO.builder()
                .seq(this.seq)
                .mealType(this.mealType.getDesc())
                .foodCode(foodDTO.getId())
                .name(foodDTO.getName())
                .energy(foodDTO.getEnergy())
                .protein(foodDTO.getEnergy())
                .fat(foodDTO.getFat())
                .carbohydrates(foodDTO.getCarbohydrates())
                .build();
    }
}
