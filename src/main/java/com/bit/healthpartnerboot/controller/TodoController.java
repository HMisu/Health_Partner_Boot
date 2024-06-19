package com.bit.healthpartnerboot.controller;

import com.bit.healthpartnerboot.dto.*;
import com.bit.healthpartnerboot.entity.CustomUserDetails;
import com.bit.healthpartnerboot.service.TodoService;
import com.bit.healthpartnerboot.service.WaterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/todo")
public class TodoController {
    private final TodoService todoService;
    private final WaterService waterService;

    @GetMapping()
    public ResponseEntity<?> getTodos(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                      @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            List<TodoDTO> todoDTOList = todoService.getTodoByDate(date, customUserDetails.getUsername());
            Integer waterIntake = waterService.getWaterIntake(date, customUserDetails.getUsername());

            DateResponseDTO responseDTO = new DateResponseDTO();
            responseDTO.setTodoList(todoDTOList);
            responseDTO.setWaterIntake(waterIntake);
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            log.error("Error processing batch request: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing batch request");
        }
    }

    @GetMapping("/week")
    public ResponseEntity<?> getTodosWeek(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam("week") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        ResponseDTO<TodoDTO> responseDTO = new ResponseDTO<>();

        try {
            LocalDate weekStart = date.with(DayOfWeek.MONDAY);
            LocalDate weekEnd = weekStart.plusDays(6);

            LocalDateTime weekStartDateTime = weekStart.atStartOfDay();
            LocalDateTime weekEndDateTime = weekEnd.atTime(23, 59, 59);

            List<TodoDTO> todoDTOList = todoService.getTodosByWeek(weekStartDateTime, weekEndDateTime, customUserDetails.getUsername());

            responseDTO.setItems(todoDTOList);
            responseDTO.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok(responseDTO);

        } catch (Exception e) {
            responseDTO.setErrorCode(100);
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @GetMapping("/d/{seq}")
    public ResponseEntity<?> getTodo(@PathVariable("seq") Integer seq,
                                     @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ResponseDTO<TodoDTO> responseDTO = new ResponseDTO<>();

        try {
            TodoDTO todoDTO = todoService.getTodo(seq, customUserDetails.getUsername());
            responseDTO.setItem(todoDTO);
            responseDTO.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            responseDTO.setErrorCode(100);
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<?> editTodo(@RequestBody EditTodoDTO editTodoDTO) {
        ResponseDTO<TodoDTO> responseDTO = new ResponseDTO<>();

        try {
            TodoDTO todo = editTodoDTO.getTodo();
            List<Integer> deleteMealList = editTodoDTO.getDeleteMealList();

            TodoDTO updateTodo = todoService.updateTodo(todo);
            todoService.deleteTodoFood(deleteMealList);

            responseDTO.setItem(updateTodo);
            responseDTO.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @PostMapping("/reg")
    public ResponseEntity<?> regTodo(@RequestBody TodoDTO todoDTO,
                                     @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ResponseDTO<TodoDTO> responseDTO = new ResponseDTO<>();

        try {
            TodoDTO todo = todoService.postTodo(customUserDetails.getUsername(), todoDTO);

            responseDTO.setItem(todo);
            responseDTO.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @DeleteMapping("/{seq}")
    public ResponseEntity<?> removeTodo(@PathVariable("seq") Integer seq) {
        ResponseDTO<String> responseDTO = new ResponseDTO<>();

        try {
            todoService.deleteTodo(seq);

            responseDTO.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            responseDTO.setErrorCode(406);
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());

            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @PostMapping("/check/reg")
    public ResponseEntity<?> regCheckList(@RequestBody CheckListDTO checkListDTO) {
        ResponseDTO<String> responseDTO = new ResponseDTO<>();

        try {
            todoService.postCheckList(checkListDTO);
            responseDTO.setItem(checkListDTO.getTodoSeq().toString());
            responseDTO.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @PutMapping("/check")
    public ResponseEntity<?> editCheckList(@RequestBody CheckListDTO checkListDTO) {
        ResponseDTO<String> responseDTO = new ResponseDTO<>();

        try {
            todoService.updateCheck(checkListDTO);

            responseDTO.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @DeleteMapping("/check/{seq}")
    public ResponseEntity<?> removeCheck(@PathVariable("seq") String seq) {
        ResponseDTO<String> responseDTO = new ResponseDTO<>();

        try {
            todoService.deleteCheckList(Integer.valueOf(seq));

            responseDTO.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            responseDTO.setErrorCode(406);
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());

            return ResponseEntity.badRequest().body(responseDTO);
        }
    }
}

