package com.bit.healthpartnerboot.entity;

import com.bit.healthpartnerboot.converter.MealTypeConverter;
import com.bit.healthpartnerboot.dto.MealType;
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
    private Float weight;

    @Column(nullable = false)
    @Convert(converter = MealTypeConverter.class)
    private MealType mealType;

    private String foodIdntCode;

    private String foodCode;
}
