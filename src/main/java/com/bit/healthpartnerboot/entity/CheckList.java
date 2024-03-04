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
@Table(name = "TB_CHECKLIST")
public class CheckList extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "checklist_seq")
    private Long seq;

    @ManyToOne
    @JoinColumn(name = "todo_seq")
    private Todo todo;

    @Column(nullable = false)
    private String text;

    private Boolean isCheck;
}
