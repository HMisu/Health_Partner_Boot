package com.bit.healthpartnerboot.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FoodDTO {
    private String id;
    private String foodCode;
    private String name;
    private String dataClassificationCode;
    private String dataClassificationName;
    private String foodOriginCode;
    private String foodOriginName;
    private String largeClassificationCode;
    private String largeClassificationName;
    private String representativeFoodCode;
    private String representativeFoodName;
    private String middleClassificationCode;
    private String middleClassificationName;
    private String smallClassificationCode;
    private String smallClassificationName;
    private String subdivisionCode;
    private String subdivisionName;
    private String nutrientContentStandard;
    private String energy;
    private String moisture;
    private String protein;
    private String fat;
    private String ashContent;
    private String carbohydrates;
    private String sugars;
    private String dietaryFiber;
    private String calcium;
    private String iron;
    private String phosphorus;
    private String potassium;
    private String sodium;
    private String vitaminA;
    private String retinol;
    private String betaCarotene;
    private String thiamine;
    private String riboflavin;
    private String niacin;
    private String vitaminC;
    private String vitaminD;
    private String cholesterol;
    private String saturatedFattyAcids;
    private String transFattyAcids;
    private String sourceCode;
    private String sourceName;
    private String foodWeight;
    private String company;
    private String dataCreationMethodCode;
    private String dataCreationMethodName;
    private String dataCreationDate;
    private String dataStandardDate;
    private String providerCode;
    private String providerName;
}