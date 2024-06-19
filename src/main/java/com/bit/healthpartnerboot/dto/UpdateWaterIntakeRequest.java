package com.bit.healthpartnerboot.dto;

import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
public class UpdateWaterIntakeRequest {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDate date;
    private Integer intake;
}