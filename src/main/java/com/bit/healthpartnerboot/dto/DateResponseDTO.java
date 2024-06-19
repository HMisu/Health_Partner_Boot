package com.bit.healthpartnerboot.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DateResponseDTO {
    private List<TodoDTO> todoList;
    private Integer waterIntake;
}
