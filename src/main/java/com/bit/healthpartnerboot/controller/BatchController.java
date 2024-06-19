package com.bit.healthpartnerboot.controller;

import com.bit.healthpartnerboot.dto.BatchResponseDTO;
import com.bit.healthpartnerboot.dto.TodoDTO;
import com.bit.healthpartnerboot.entity.CustomUserDetails;
import com.bit.healthpartnerboot.service.MemberService;
import com.bit.healthpartnerboot.service.TodoService;
import com.bit.healthpartnerboot.service.WaterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/batch")
@RequiredArgsConstructor
public class BatchController {
    private final TodoService todoService;
    private final WaterService waterService;
    private final MemberService memberService;

    @GetMapping("/process")
    public ResponseEntity<?> processBatch(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                          @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            LocalDate weekStart = date.with(DayOfWeek.MONDAY);
            LocalDate weekEnd = weekStart.plusDays(6);

            LocalDateTime weekStartDateTime = weekStart.atStartOfDay();
            LocalDateTime weekEndDateTime = weekEnd.atTime(23, 59, 59);

            List<TodoDTO> todoDTOList = todoService.getTodosByWeek(weekStartDateTime, weekEndDateTime, customUserDetails.getUsername());

            Integer waterIntake = waterService.getWaterIntake(date, customUserDetails.getUsername());

            List<Map<String, Object>> bmiGraphData = memberService.getBmiGraph(customUserDetails.getUsername());

            BatchResponseDTO batchResponseDTO = new BatchResponseDTO();
            batchResponseDTO.setTodoList(todoDTOList);
            batchResponseDTO.setWaterIntake(waterIntake);
            batchResponseDTO.setBmiGraphData(bmiGraphData);

            return ResponseEntity.ok(batchResponseDTO);
        } catch (Exception e) {
            log.error("Error processing batch request: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing batch request");
        }
    }
}
