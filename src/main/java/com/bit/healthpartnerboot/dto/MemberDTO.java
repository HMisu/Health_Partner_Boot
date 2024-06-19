package com.bit.healthpartnerboot.dto;

import com.bit.healthpartnerboot.entity.Member;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDTO {
    private Integer seq;
    private String email;
    private String password;
    private String name;
    private String gender;
    private int age;
    private float height;
    private float weight;
    private float bmi;
    private String activityLevel;
    private String imgAddress;
    private int goalWater;
    private String role;
    private String provider;
    private Boolean isActive;
    private String lastLoginDate;
    private String token;
    private String currentPassword;

    public Member toEntity() {
        return Member.builder()
                .seq(this.seq)
                .email(this.email)
                .password(this.password)
                .name(this.name)
                .gender(this.gender)
                .age(this.age)
                .height(this.height)
                .weight(this.weight)
                .bmi(this.bmi)
                .activityLevel(this.activityLevel)
                .imgAddress(this.imgAddress)
                .goalWater(this.goalWater)
                .role(Role.ofCode(role))
                .provider(this.provider)
                .isActive(this.isActive)
                .lastLoginDate(LocalDateTime.parse(this.lastLoginDate))
                .build();
    }

}
