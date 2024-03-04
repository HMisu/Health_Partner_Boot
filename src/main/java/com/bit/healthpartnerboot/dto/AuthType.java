package com.bit.healthpartnerboot.dto;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum AuthType {
    JOIN("가입", "J"),
    CHANGE_PWD("비밀번호변경", "PWD");

    private final String desc;
    private final String legacyCode;

    AuthType(String desc, String legacyCode) {
        this.desc = desc;
        this.legacyCode = legacyCode;
    }

    public static AuthType ofCode(String legacyCode) {
        return Arrays.stream(AuthType.values())
                .filter(v -> v.getLegacyCode().equals(legacyCode))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 인증 코드입니다."));
    }
}
