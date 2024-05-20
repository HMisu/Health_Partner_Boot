package com.bit.healthpartnerboot.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@AllArgsConstructor
@RedisHash(value = "refreshToken", timeToLive = 60 * 60 * 24 * 3)
public class RefreshToken {
    @Id
    private String refreshToken;

    @Indexed
    private String memberEmail;
}