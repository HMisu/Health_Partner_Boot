package com.bit.healthpartnerboot.service.impl;

import com.bit.healthpartnerboot.entity.Member;
import com.bit.healthpartnerboot.entity.Water;
import com.bit.healthpartnerboot.repository.jpa.WaterRepository;
import com.bit.healthpartnerboot.service.WaterService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class WaterServiceImpl implements WaterService {
    private final WaterRepository waterRepository;

    @Override
    public int getWaterIntake(LocalDate date, String memberEmail) {
        return Optional.ofNullable(waterRepository.findByDate(date, memberEmail)).orElse(0);
    }


    @Override
    @Transactional
    public int updateWaterIntake(int intake, LocalDate date, Member member) {
        Integer existingInatke = waterRepository.findByDate(date, member.getEmail());
        if (existingInatke == null) {
            Water water = Water.builder()
                    .member(member)
                    .intake(intake)
                    .date(date)
                    .build();
            waterRepository.save(water);
        } else {
            waterRepository.updateWaterIntake(intake, date, member.getEmail());
        }

        return intake;
    }
}
