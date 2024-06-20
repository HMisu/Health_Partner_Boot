package com.bit.healthpartnerboot.hash;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@Builder
@AllArgsConstructor
@RedisHash(value = "emailAuth", timeToLive = 60 * 30)
public class EmailAuth {
    @Id
    private String verifyCode;

    @Indexed
    private String email;
}
