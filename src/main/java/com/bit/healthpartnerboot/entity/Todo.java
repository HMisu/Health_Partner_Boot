package com.bit.healthpartnerboot.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "TB_TODO")
public class Todo extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_seq")
    private Long seq;

    @ManyToOne
    @JoinColumn(name = "member_seq")
    private Member member;

    @Column(nullable = false)
    private String title;

    private String diary;

    private LocalDateTime date;

    @OneToMany(mappedBy = "todo", cascade = CascadeType.ALL)
    @OrderBy("isCheck asc")
    @JsonManagedReference
    private List<CheckList> checkList;
}
