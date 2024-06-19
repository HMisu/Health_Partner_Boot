package com.bit.healthpartnerboot.dto;

import com.bit.healthpartnerboot.entity.CheckList;
import com.bit.healthpartnerboot.entity.Todo;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CheckListDTO {
    private Integer seq;
    private Integer todoSeq;
    private String text;
    private Boolean isCheck;

    public CheckList toEntity(Todo todo) {
        return CheckList.builder()
                .seq(this.seq)
                .todo(todo)
                .text(this.text)
                .isCheck(this.isCheck)
                .build();
    }
}
