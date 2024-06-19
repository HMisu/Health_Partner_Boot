package com.bit.healthpartnerboot.dto;

import com.bit.healthpartnerboot.entity.Member;
import com.bit.healthpartnerboot.entity.Todo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class TodoDTO {
    private Integer seq;
    private String title;
    private String diary;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime date;
    private List<CheckListDTO> checkList;
    private List<TodoFoodDTO> mealList;

    public Todo toEntity(Member member) {
        return Todo.builder()
                .seq(this.seq)
                .title(this.title)
                .date(this.date)
                .diary(this.diary)
                .member(member)
                // .checkList(this.checkList.toEntity())
                // .mealList(this.mealList)
                .build();
    }
}
