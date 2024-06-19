package com.bit.healthpartnerboot.entity;

import com.bit.healthpartnerboot.dto.MemberHistoryDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(value = AuditingEntityListener.class)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "TB_MEMBER_HISTORY")
public class MemberHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_seq")
    private Integer seq;

    private Float height;

    private Float weight;

    private Float bmi;

    @ManyToOne
    @JoinColumn(name = "member_seq")
    private Member member;

    @CreatedDate
    @Column(columnDefinition = "datetime(6) default now(6)", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    public MemberHistoryDTO toDTO() {
        return MemberHistoryDTO.builder()
                .seq(this.seq)
                .height(this.height)
                .weight(this.weight)
                .bmi(this.bmi)
                .date(this.createdAt)
                .build();
    }
}
