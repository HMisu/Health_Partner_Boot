package com.bit.healthpartnerboot.entity;

import com.bit.healthpartnerboot.converter.RoleConverter;
import com.bit.healthpartnerboot.dto.MemberDTO;
import com.bit.healthpartnerboot.dto.Role;
import com.bit.healthpartnerboot.listener.MemberEntityListener;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.Persistable;

import java.time.LocalDateTime;

@Entity
@EntityListeners(value = MemberEntityListener.class)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "TB_MEMBER")
public class Member implements Persistable<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_seq")
    private Integer seq;
    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    @Setter
    private String name;
    @Setter
    private String gender;
    @Setter
    private int age;
    @Setter
    private Float height;
    @Setter
    private Float weight;
    @Setter
    private Float bmi;
    @Setter
    private String activityLevel;
    private String imgAddress;
    private int goalWater;
    @Column(nullable = false)
    @Convert(converter = RoleConverter.class)
    private Role role;
    private String provider;
    private Boolean isActive;
    private LocalDateTime lastLoginDate;

    @Setter
    @Transient
    private float originalHeight;
    @Setter
    @Transient
    private float originalWeight;
    @Setter
    @Transient
    private float originalBmi;

    @Override
    public boolean isNew() {
        return this.seq == null;
    }

    @Override
    public Integer getId() {
        return seq;
    }

    public MemberDTO toDTO() {
        MemberDTO.MemberDTOBuilder builder = MemberDTO.builder()
                .seq(this.seq)
                .email(this.email)
                .password(this.password)
                .name(this.name)
                .gender(this.gender)
                .age(this.age)
                .activityLevel(this.activityLevel)
                .imgAddress(this.imgAddress)
                .goalWater(this.goalWater)
                .role(this.role.toString())
                .provider(this.provider)
                .isActive(this.isActive)
                .lastLoginDate(this.lastLoginDate.toString());

        if (height != null) {
            builder.height(height);
        }

        if (weight != null) {
            builder.weight(weight);
        }

        if (bmi != null) {
            builder.bmi(bmi);
        }

        return builder.build();
    }
}
