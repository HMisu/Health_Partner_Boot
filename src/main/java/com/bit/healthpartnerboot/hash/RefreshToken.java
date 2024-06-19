package com.bit.healthpartnerboot.hash;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@Setter
@RedisHash(value = "refreshToken", timeToLive = 60 * 60 * 24 * 3)
public class RefreshToken {
    @Id
    private String refreshToken;

    @Indexed
    private String memberEmail;

    @JsonCreator
    public RefreshToken(@JsonProperty("refreshToken") String refreshToken, @JsonProperty("memberEmail") String memberEmail) {
        this.refreshToken = refreshToken;
        this.memberEmail = memberEmail;
    }
}