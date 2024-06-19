package com.bit.healthpartnerboot.repository.jpa;

import com.bit.healthpartnerboot.entity.Water;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface WaterRepository extends JpaRepository<Water, Integer> {
    @Query(value = "select w.intake from Water w where w.date = :date and w.member.email=:memberEmail")
    Integer findByDate(LocalDate date, String memberEmail);

    @Query(value = "update Water w set w.intake = :intake where w.date = :date and w.member.email=:memberEmail")
    @Modifying
    void updateWaterIntake(int intake, LocalDate date, String memberEmail);
}
