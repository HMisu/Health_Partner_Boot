package com.bit.healthpartnerboot.entity;

import com.bit.healthpartnerboot.converter.RoleConverter;
import com.bit.healthpartnerboot.dto.MemberDTO;
import com.bit.healthpartnerboot.dto.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "TB_MEMBER")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_seq")
    private Long seq;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer age;

    private String imgAddress;
    
    private Integer goalWater;

    private Integer goalPedometer;

    @Convert(converter = RoleConverter.class)
    private Role role;

    private Boolean isEmailAuth;

    public MemberDTO toDTO() {
        return MemberDTO.builder()
                .seq(this.seq)
                .name(this.name)
                .password(this.password)
                .role(this.role.getDesc())
                .build();
    }

    public void emailVerifiedSuccess() {
        this.isEmailAuth = true;
    }
}
