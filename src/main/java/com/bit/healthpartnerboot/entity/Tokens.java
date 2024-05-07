package com.bit.healthpartnerboot.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Tokens {
    private String accessToken;
    private String refreshToken;
    private Date accessTokenExpireDate;
    private Date refreshTokenExpireDate;

    // 생성자, getter, setter 등의 필요한 메서드를 추가할 수 있습니다.

    public Tokens(String accessToken, String refreshToken, Date accessTokenExpireDate, Date refreshTokenExpireDate) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.accessTokenExpireDate = accessTokenExpireDate;
        this.refreshTokenExpireDate = refreshTokenExpireDate;
    }

    // getter 및 setter 등의 필요한 메서드를 추가할 수 있습니다.
}