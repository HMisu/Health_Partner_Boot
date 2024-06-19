package com.bit.healthpartnerboot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "TB_WATER")
public class Water {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "water_seq")
    private Integer seq;

    @ManyToOne
    @JoinColumn(name = "member_seq")
    private Member member;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int intake;

    private LocalDate date;
}
