package com.bit.healthpartnerboot.repository;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class RefreshTokenRepository {

    private final RedisTemplate<String, String> redisTemplate;
    private static final String REFRESH_TOKEN_PREFIX = "refreshToken:";

    public RefreshTokenRepository(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveRefreshToken(String token) {
        String key = REFRESH_TOKEN_PREFIX + token;
        // Redis에 토큰을 저장합니다.
        redisTemplate.opsForValue().set(key, token);
        // 만료 시간 설정 (예: 30일)
        redisTemplate.expire(key, 30, TimeUnit.DAYS);
    }

    public boolean isRefreshTokenExists(String token) {
        String key = REFRESH_TOKEN_PREFIX + token;
        // Redis에 해당 토큰이 존재하는지 확인합니다.
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
    

    public void deleteRefreshToken(String refreshToken) {
        String key = REFRESH_TOKEN_PREFIX + refreshToken;
        redisTemplate.delete(key);
    }
}