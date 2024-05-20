package com.bit.healthpartnerboot.entity;

import com.bit.healthpartnerboot.converter.RoleConverter;
import com.bit.healthpartnerboot.dto.MemberDTO;
import com.bit.healthpartnerboot.dto.Role;
import com.bit.healthpartnerboot.listener.MemberEntityListener;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@EntityListeners(value = MemberEntityListener.class)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "TB_MEMBER")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_seq")
    private Integer seq;
    @Column(unique = true, nullable = false)
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
    @Column(nullable = false)
    @Convert(converter = RoleConverter.class)
    private Role role;
    private String provider;
    private Boolean isActive;
    private LocalDateTime lastLoginDate;

    public MemberDTO toDTO() {
        return MemberDTO.builder()
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
                .role(this.role.toString())
                .provider(this.provider)
                .isActive(this.isActive)
                .lastLoginDate(this.lastLoginDate.toString())
                .build();
    }
}
