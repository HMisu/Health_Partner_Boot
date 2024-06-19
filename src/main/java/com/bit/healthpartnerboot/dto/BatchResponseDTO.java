package com.bit.healthpartnerboot.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class BatchResponseDTO {
    private List<TodoDTO> todoList;
    private Integer waterIntake;
    private List<Map<String, Object>> bmiGraphData;
}
