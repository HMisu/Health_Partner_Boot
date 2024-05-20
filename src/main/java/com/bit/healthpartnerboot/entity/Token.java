package com.bit.healthpartnerboot.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Token {
    private String accessToken;
    private String refreshToken;
    private Date accessTokenExpireDate;
    private Date refreshTokenExpireDate;

    public Token(String accessToken, String refreshToken, Date accessTokenExpireDate, Date refreshTokenExpireDate) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.accessTokenExpireDate = accessTokenExpireDate;
        this.refreshTokenExpireDate = refreshTokenExpireDate;
    }
}