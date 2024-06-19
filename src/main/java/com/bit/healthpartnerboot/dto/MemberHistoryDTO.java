package com.bit.healthpartnerboot.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class MemberHistoryDTO {
    private Integer seq;
    private float height;
    private float weight;
    private float bmi;
    private LocalDateTime date;
}
