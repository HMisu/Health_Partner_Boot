package com.bit.healthpartnerboot.dto;

import com.bit.healthpartnerboot.entity.Food;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodDTO {
    private String code;
    private String upperFdGrupp;
    private String fdGrupp;
    private String name;
    private Float weight;
    private Integer foodCnt;
    private String imgAddress;

    public Food toEntity() {
        return Food.builder()
                .code(this.code)
                .upperFdGrupp(this.upperFdGrupp)
                .fdGrupp(this.fdGrupp)
                .name(this.name)
                .weight(this.weight)
                .foodCnt(this.foodCnt)
                .imgAddress(this.imgAddress)
                .build();
    }
}
