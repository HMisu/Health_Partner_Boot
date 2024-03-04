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
@Table(name = "TB_FOOD")
public class Food {
    @Id
    @Column(name = "food_code")
    private String code;

    @Comment("대분류")
    private String upperFdGrupp;

    @Comment("중분류")
    private String fdGrupp;

    private String name;

    private Float weight;

    private Integer foodCnt;

    private String imgAddress;
}
