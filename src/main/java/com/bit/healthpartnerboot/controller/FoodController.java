package com.bit.healthpartnerboot.controller;

import com.bit.healthpartnerboot.dto.FoodDTO;
import com.bit.healthpartnerboot.dto.ResponseDTO;
import com.bit.healthpartnerboot.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/food")
@RequiredArgsConstructor
public class FoodController {
    private FoodService foodService;

    @PostMapping("/init")
    public ResponseEntity<?> save() {
        ResponseDTO<FoodDTO> responseDTO = new ResponseDTO<>();

        try {
            foodService.saveToDatabase();

            responseDTO.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            responseDTO.setErrorCode(300);
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

}
