package com.bit.healthpartnerboot.dto;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Role {
    ROLE_USER("사용자", "ROLE_USER"),
    ROLE_ADMIN("관리자", "ROLE_ADMIN");

    private final String desc;
    private final String legacyCode;

    Role(String desc, String legacyCode) {
        this.desc = desc;
        this.legacyCode = legacyCode;
    }

    public static Role ofCode(String legacyCode) {
        return Arrays.stream(Role.values())
                .filter(v -> v.getLegacyCode().equals(legacyCode))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 권한 코드입니다."));
    }
}
