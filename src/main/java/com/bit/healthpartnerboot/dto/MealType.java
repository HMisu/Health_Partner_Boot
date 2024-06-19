package com.bit.healthpartnerboot.dto;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum MealType {
    BREAKFAST("breakfast", "B"),
    LUNCH("lunch", "L"),
    DINNER("dinner", "D"),
    SNACK("snack", "S");

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
