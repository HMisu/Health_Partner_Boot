package com.bit.healthpartnerboot.entity;

import com.bit.healthpartnerboot.dto.MemberDTO;
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
    private long seq;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private int height;

    @Column(nullable = false)
    private int weight;

    private int goalPedometer;

    private String role;

    private boolean isEmailAuth;

    public MemberDTO toDTO() {
        return MemberDTO.builder()
                .seq(this.seq)
                .username(this.username)
                .password(this.password)
                .role(this.role)
                .build();
    }

    public void emailVerifiedSuccess() {
        this.isEmailAuth = true;
    }
}
