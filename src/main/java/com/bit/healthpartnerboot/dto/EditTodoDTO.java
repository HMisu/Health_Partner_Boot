package com.bit.healthpartnerboot.dto;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class EditTodoDTO {
    private TodoDTO todo;
    private List<Integer> deleteMealList;
}