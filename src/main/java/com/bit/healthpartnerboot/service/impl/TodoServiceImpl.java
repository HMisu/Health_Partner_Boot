package com.bit.healthpartnerboot.service.impl;

import com.bit.healthpartnerboot.dto.CheckListDTO;
import com.bit.healthpartnerboot.dto.TodoDTO;
import com.bit.healthpartnerboot.dto.TodoFoodDTO;
import com.bit.healthpartnerboot.entity.Member;
import com.bit.healthpartnerboot.entity.Todo;
import com.bit.healthpartnerboot.entity.TodoFood;
import com.bit.healthpartnerboot.repository.jpa.CheckListRepository;
import com.bit.healthpartnerboot.repository.jpa.MemberRepository;
import com.bit.healthpartnerboot.repository.jpa.TodoFoodRepository;
import com.bit.healthpartnerboot.repository.jpa.TodoRepository;
import com.bit.healthpartnerboot.repository.mongo.FoodRepository;
import com.bit.healthpartnerboot.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class TodoServiceImpl implements TodoService {
    private final TodoFoodRepository todoFoodRepository;
    private final TodoRepository todoRepository;
    private final FoodRepository foodRepository;
    private final CheckListRepository checkListRepository;
    private final MemberRepository memberRepository;

    @Override
    public List<TodoDTO> getTodoByDate(LocalDate date, String writer) {
        int year = date.getYear();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();
        List<Todo> todoList = todoRepository.findAllByDate(year, month, day, writer);

        List<TodoDTO> todoDTOList = new ArrayList<>();

        todoList.forEach(todo -> {
            todoDTOList.add(todo.toDTO(null));
        });

        return todoDTOList;
    }

    @Override
    public List<Integer> getSeqByDate(LocalDate date, String writer) {
        int year = date.getYear();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();

        return todoRepository.findSeqByDate(year, month, day, writer);
    }

    @Override
    public List<TodoFood> getTodoFoodList(List<Integer> todoSeqList) {
        List<TodoFood> mealList = new ArrayList<>();

        todoSeqList.forEach(seq -> {
            mealList.addAll(todoFoodRepository.findAllByTodo(seq));
        });

        return mealList;
    }


    @Override
    public List<TodoDTO> getTodosByWeek(LocalDateTime weekStart, LocalDateTime weekEnd, String writer) {  // 로그 정보 출력
        List<Todo> todoList = todoRepository.findAllByWeek(weekStart, weekEnd, writer);

        List<TodoDTO> todoDTOList = new ArrayList<>();

        todoList.forEach(todo -> {
            todoDTOList.add(todo.toDTO(null));
        });

        return todoDTOList;
    }

    @Override
    public TodoDTO getTodo(Integer seq, String writer) {
        Todo todo = todoRepository.findBySeq(seq);

        if (todo == null) {
            return null;
        }

        if (!todo.getMember().getEmail().equals(writer)) {
            return null;
        }

        List<TodoFoodDTO> mealDTOList = new ArrayList<>();
        List<TodoFood> mealList = todoFoodRepository.findAllByTodo(seq);
        mealList.forEach(meal -> {
            mealDTOList.add(meal.toDTO(foodRepository.findById(meal.getFoodCode()).get().toDTO()));
        });

        return todo.toDTO(mealDTOList);
    }

    @Override
    @Transactional
    public TodoDTO postTodo(String writer, TodoDTO todoDTO) {
        Member member = memberRepository.findByEmail(writer).get();
        Todo todo = todoDTO.toEntity(member);
        Todo savedTodo = todoRepository.save(todo);

        postFoodList(savedTodo, todoDTO.getMealList());

        return savedTodo.toDTO(null);
    }

    @Override
    @Transactional
    public TodoDTO updateTodo(TodoDTO todoDTO) {
        todoRepository.updateTodo(todoDTO.getSeq(), todoDTO.getTitle(), todoDTO.getDiary(), todoDTO.getDate());
        Todo updatedTodo = todoRepository.findBySeq(todoDTO.getSeq());
        postFoodList(updatedTodo, todoDTO.getMealList());

        return updatedTodo.toDTO(null);
    }

    @Override
    public void postCheckList(CheckListDTO checkListDTO) {
        Todo todo = todoRepository.findBySeq(checkListDTO.getTodoSeq());
        checkListRepository.saveAndFlush(checkListDTO.toEntity(todo));
    }

    @Override
    @Transactional
    public void updateCheck(CheckListDTO checkListDTO) {
        checkListRepository.updateIsCheckAndText(checkListDTO.getSeq(), checkListDTO.getText(), checkListDTO.getIsCheck());
    }

    @Override
    @Transactional
    public void deleteCheckList(Integer seq) {
        checkListRepository.deleteBySeq(seq);
    }

    @Override
    public void postFoodList(Todo todo, List<TodoFoodDTO> todoFoodDTOList) {
        List<TodoFood> todoFoodList = new ArrayList<>();

        todoFoodDTOList.forEach(food -> {
            if (food.getSeq() == null) {
                String mealType = food.getMealType();
                food.setMealType(mealType.toUpperCase());
                TodoFood todoFood = food.toEntity(todo);
                todoFoodList.add(todoFood);
            }
        });

        todoFoodRepository.saveAllAndFlush(todoFoodList);
    }

    @Override
    @Transactional
    public void deleteTodo(Integer seq) {
        todoFoodRepository.deleteByTodoSeq(seq);
        todoRepository.deleteBySeq(seq);
    }

    @Override
    @Transactional
    public void deleteTodoFood(List<Integer> deleteMealList) {
        if (!deleteMealList.isEmpty()) {
            todoFoodRepository.deleteBySeqList(deleteMealList);
        }
    }
}
