package com.bit.healthpartnerboot.service;

import com.bit.healthpartnerboot.entity.Member;

import java.time.LocalDate;

public interface WaterService {
    int getWaterIntake(LocalDate date, String memberEmail);

    int updateWaterIntake(int intake, LocalDate date, Member member);
}
