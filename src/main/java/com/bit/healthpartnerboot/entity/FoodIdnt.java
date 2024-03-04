package com.bit.healthpartnerboot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "TB_FOOD_IDNT")
public class FoodIdnt {
    @Id
    @Column(name = "food_idnt_code")
    private String code;

    private String name;

    private Float weight;

    @Comment("에너지")
    private Float energy;

    @Comment("수분")
    private Float water;

    @Comment("단백질")
    private Float prot;

    @Comment("탄수화물")
    private Float carbohydrate;

    @Comment("총 당류")
    private Float sugar;

    @Comment("총 식이섬유")
    private Float fibtg;

    @Comment("총 지방산")
    private Float fafref;

    @Comment("총 포화지방산")
    private Float faessf;

    @Comment("총 단일 불포화지방산")
    private Float famsf;

    @Comment("총 다중 불포화지방산")
    private Float fapuf;
}
