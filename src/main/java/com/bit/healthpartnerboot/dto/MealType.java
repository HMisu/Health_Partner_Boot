package com.bit.healthpartnerboot.dto;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum MealType {
    BREAKFAST("아침", "B"),
    LUNCH("점심", "L"),
    DINNER("저녁", "D"),
    SNACK("간식", "S");

    private final String desc;
    private final String legacyCode;

    MealType(String desc, String legacyCode) {
        this.desc = desc;
        this.legacyCode = legacyCode;
    }

    public static MealType ofCode(String legacyCode) {
        return Arrays.stream(MealType.values())
                .filter(v -> v.getLegacyCode().equals(legacyCode))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 식사 코드입니다."));
    }
}
