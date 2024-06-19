package com.bit.healthpartnerboot.controller;

import com.bit.healthpartnerboot.dto.FoodDTO;
import com.bit.healthpartnerboot.dto.RecommendFoodDTO;
import com.bit.healthpartnerboot.dto.ResponseDTO;
import com.bit.healthpartnerboot.entity.CustomUserDetails;
import com.bit.healthpartnerboot.entity.Member;
import com.bit.healthpartnerboot.service.FoodService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/food")
@RequiredArgsConstructor
public class FoodController {
    private final FoodService foodService;

    @GetMapping("/search")
    public ResponseEntity<?> getBoardList(@RequestParam("keyword") String keyword) {
        ResponseDTO<FoodDTO> responseDTO = new ResponseDTO<>();

        try {
            List<FoodDTO> foodDTOList = foodService.searchFood(keyword.strip());

            responseDTO.setItems(foodDTOList);
            responseDTO.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            responseDTO.setErrorCode(401);
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());

            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @GetMapping("/recommend")
    public ResponseEntity<?> recommendFoods(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        try {
            Member member = customUserDetails.getMember();

            if (member.getAge() == 0 || member.getHeight() == null || member.getWeight() == null || member.getGender() == null) {
                String str = "추가적인 회원 정보 입력이 필요합니다.";
                return ResponseEntity.badRequest().body(str);
            }

            RecommendFoodDTO responseDTO = foodService.getRecommendedFoods(member);
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            log.error("Error processing batch request: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing batch request");
        }
    }
}
