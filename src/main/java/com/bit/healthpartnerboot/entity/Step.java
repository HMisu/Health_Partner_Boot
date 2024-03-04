package com.bit.healthpartnerboot.entity;

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
@Table(name = "TB_STEP")
public class Step extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "step_seq")
    private Long seq;

    @ManyToOne
    @JoinColumn(name = "member_seq")
    private Member member;

    @Column(nullable = false)
    private Integer stepNum;
}
