package com.bit.healthpartnerboot.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class MainFoodIngredientsId implements Serializable {
    private String mainFood;
    private String foodIdnt;
}
