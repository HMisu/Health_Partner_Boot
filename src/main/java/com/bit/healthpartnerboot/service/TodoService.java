package com.bit.healthpartnerboot.service;

import com.bit.healthpartnerboot.dto.CheckListDTO;
import com.bit.healthpartnerboot.dto.TodoDTO;
import com.bit.healthpartnerboot.dto.TodoFoodDTO;
import com.bit.healthpartnerboot.entity.Todo;
import com.bit.healthpartnerboot.entity.TodoFood;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TodoService {
    List<TodoDTO> getTodoByDate(LocalDate date, String writer);

    List<Integer> getSeqByDate(LocalDate date, String writer);

    List<TodoFood> getTodoFoodList(List<Integer> todoSeqList);

    List<TodoDTO> getTodosByWeek(LocalDateTime weekStart, LocalDateTime weekEnd, String writer);

    TodoDTO getTodo(Integer seq, String writer);

    TodoDTO postTodo(String writer, TodoDTO todoDTO);

    TodoDTO updateTodo(TodoDTO todoDTO);

    void deleteTodo(Integer seq);

    void postCheckList(CheckListDTO checkListDTO);

    void updateCheck(CheckListDTO checkListDTO);

    void deleteCheckList(Integer seq);

    void postFoodList(Todo todo, List<TodoFoodDTO> todoFoodDTOList);

    void deleteTodoFood(List<Integer> deleteMealList);
}
