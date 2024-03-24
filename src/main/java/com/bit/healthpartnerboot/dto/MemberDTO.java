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
    private Integer age;
    private Integer height;
    private Integer weight;
    private Float bmi;
    private String imgAddress;
    private Integer goalWater;
    private Integer goalPedometer;
    private String role;
    private Boolean isEmailAuth;
    private Boolean isActive;
    private String lastLoginDate;
    private String token;

    public Member toEntity() {
        return Member.builder()
                .seq(this.seq)
                .email(this.email)
                .password(this.password)
                .name(this.name)
                .age(this.age)
                .height(this.height)
                .weight(this.weight)
                .bmi(this.bmi)
                .imgAddress(this.imgAddress)
                .goalWater(this.goalWater)
                .goalPedometer(this.goalPedometer)
                .role(Role.ofCode(role))
                .isEmailAuth(this.isEmailAuth)
                .isActive(this.isActive)
                .lastLoginDate(LocalDateTime.parse(this.lastLoginDate))
                .build();
    }

}
