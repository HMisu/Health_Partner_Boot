package com.bit.healthpartnerboot.document;

import com.bit.healthpartnerboot.dto.FoodDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "Food")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Food {
    @Id
    private String id;
    @Field("식품코드")
    private String foodCode;
    @Field("식품명")
    private String name;
    @Field("데이터구분코드")
    private String dataClassificationCode;
    @Field("데이터구분명")
    private String dataClassificationName;
    @Field("식품기원코드")
    private String foodOriginCode;
    @Field("식품기원명")
    private String foodOriginName;
    @Field("식품대분류코드")
    private String largeClassificationCode;
    @Field("식품대분류명")
    private String largeClassificationName;
    @Field("대표식품코드")
    private String representativeFoodCode;
    @Field("대표식품명")
    private String representativeFoodName;
    @Field("식품중분류코드")
    private String middleClassificationCode;
    @Field("식품중분류명")
    private String middleClassificationName;
    @Field("식품소분류코드")
    private String smallClassificationCode;
    @Field("식품소분류명")
    private String smallClassificationName;
    @Field("식품세분류코드")
    private String subdivisionCode;
    @Field("식품세분류명")
    private String subdivisionName;
    @Field("영양성분함량기준량")
    private String nutrientContentStandard;
    @Field("에너지(kcal)")
    private String energy;
    @Field("수분(g)")
    private String moisture;
    @Field("단백질(g)")
    private String protein;
    @Field("지방(g)")
    private String fat;
    @Field("회분(g)")
    private String ashContent;
    @Field("탄수화물(g)")
    private String carbohydrates;
    @Field("당류(g)")
    private String sugars;
    @Field("식이섬유(g)")
    private String dietaryFiber;
    @Field("칼슘(mg)")
    private String calcium;
    @Field("철(mg)")
    private String iron;
    @Field("인(mg)")
    private String phosphorus;
    @Field("칼륨(mg)")
    private String potassium;
    @Field("나트륨(mg)")
    private String sodium;
    @Field("비타민 A(μg RAE)")
    private String vitaminA;
    @Field("레티놀(μg)")
    private String retinol;
    @Field("베타카로틴(μg)")
    private String betaCarotene;
    @Field("티아민(mg)")
    private String thiamine;
    @Field("리보플라빈(mg)")
    private String riboflavin;
    @Field("니아신(mg)")
    private String niacin;
    @Field("비타민 C(mg)")
    private String vitaminC;
    @Field("비타민 D(μg)")
    private String vitaminD;
    @Field("콜레스테롤(mg)")
    private String cholesterol;
    @Field("포화지방산(g)")
    private String saturatedFattyAcids;
    @Field("트랜스지방산(g)")
    private String transFattyAcids;
    @Field("출처코드")
    private String sourceCode;
    @Field("출처명")
    private String sourceName;
    @Field("식품중량")
    private String foodWeight;
    @Field("업체명")
    private String company;
    @Field("데이터생성방법코드")
    private String dataCreationMethodCode;
    @Field("데이터생성방법명")
    private String dataCreationMethodName;
    @Field("데이터생성일자")
    private String dataCreationDate;
    @Field("데이터기준일자")
    private String dataStandardDate;
    @Field("제공기관코드")
    private String providerCode;
    @Field("제공기관명")
    private String providerName;

    public FoodDTO toDTO() {
        return FoodDTO.builder()
                .id(this.id)
                .foodCode(this.foodCode)
                .name(this.name)
                .dataClassificationCode(this.dataClassificationCode)
                .dataClassificationName(this.dataClassificationName)
                .foodOriginCode(this.foodOriginCode)
                .foodOriginName(this.foodOriginName)
                .largeClassificationCode(this.largeClassificationCode)
                .largeClassificationName(this.largeClassificationName)
                .representativeFoodCode(this.representativeFoodCode)
                .representativeFoodName(this.representativeFoodName)
                .middleClassificationCode(this.middleClassificationCode)
                .middleClassificationName(this.middleClassificationName)
                .smallClassificationCode(this.smallClassificationCode)
                .smallClassificationName(this.smallClassificationName)
                .subdivisionCode(this.subdivisionCode)
                .subdivisionName(this.subdivisionName)
                .nutrientContentStandard(this.nutrientContentStandard)
                .energy(this.energy)
                .moisture(this.moisture)
                .protein(this.protein)
                .fat(this.fat)
                .ashContent(this.ashContent)
                .carbohydrates(this.carbohydrates)
                .sugars(this.sugars)
                .dietaryFiber(this.dietaryFiber)
                .calcium(this.calcium)
                .iron(this.iron)
                .phosphorus(this.phosphorus)
                .potassium(this.potassium)
                .sodium(this.sodium)
                .vitaminA(this.vitaminA)
                .retinol(this.retinol)
                .betaCarotene(this.betaCarotene)
                .thiamine(this.thiamine)
                .riboflavin(this.riboflavin)
                .niacin(this.niacin)
                .vitaminC(this.vitaminC)
                .vitaminD(this.vitaminD)
                .cholesterol(this.cholesterol)
                .saturatedFattyAcids(this.saturatedFattyAcids)
                .transFattyAcids(this.transFattyAcids)
                .sourceCode(this.sourceCode)
                .sourceName(this.sourceName)
                .foodWeight(this.foodWeight)
                .company(this.company)
                .dataCreationMethodCode(this.dataCreationMethodCode)
                .dataCreationMethodName(this.dataCreationMethodName)
                .dataCreationDate(this.dataCreationDate)
                .dataStandardDate(this.dataStandardDate)
                .providerCode(this.providerCode)
                .providerName(this.providerName)
                .build();
    }
}