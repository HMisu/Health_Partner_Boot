package com.bit.healthpartnerboot.controller;

import com.bit.healthpartnerboot.dto.ResponseDTO;
import com.bit.healthpartnerboot.dto.UpdateWaterIntakeRequest;
import com.bit.healthpartnerboot.entity.CustomUserDetails;
import com.bit.healthpartnerboot.service.WaterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Slf4j
@RestController
@RequestMapping("/water")
@RequiredArgsConstructor
public class WaterController {
    private final WaterService waterService;

    @GetMapping("/intake")
    public ResponseEntity<?> getWaterIntake(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate date) {
        ResponseDTO<Integer> responseDTO = new ResponseDTO<>();

        try {
            Integer intake = waterService.getWaterIntake(date, customUserDetails.getUsername());

            responseDTO.setItem(intake);
            responseDTO.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            responseDTO.setErrorCode(401);
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());

            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @PutMapping("/intake")
    public ResponseEntity<?> updateWaterIntake(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                               @RequestBody UpdateWaterIntakeRequest request) {
        ResponseDTO<Integer> responseDTO = new ResponseDTO<>();

        try {
            Integer updateIntake = waterService.updateWaterIntake(request.getIntake(), request.getDate(), customUserDetails.getMember());

            responseDTO.setItem(updateIntake);
            responseDTO.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            responseDTO.setErrorCode(401);
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());

            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

}
