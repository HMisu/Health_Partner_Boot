package com.bit.healthpartnerboot.entity;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "refreshToken", timeToLive = 14440)
public class RefreshToken {

    @Id
    private String refreshToken;
    private Integer userId;

    public RefreshToken(String refreshToken, Integer userId) {
        this.refreshToken = refreshToken;
        this.userId = userId;
    }
}