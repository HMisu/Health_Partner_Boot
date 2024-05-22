package com.bit.healthpartnerboot.hash;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@AllArgsConstructor
@RedisHash(value = "blackList")
public class LogoutToken {
    @Id
    private String id;

    @Indexed
    private String accessToken;
}