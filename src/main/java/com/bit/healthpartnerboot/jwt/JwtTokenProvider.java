package com.bit.healthpartnerboot.jwt;

import com.bit.healthpartnerboot.entity.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${jwt.secretKey}")
    private String SECRET_KEY;
    SecretKey key = Jwts.SIG.HS256.key().build();

    public String create(Member member) {
        Date expireDate = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));

        return Jwts.builder()
                .signWith(key)
                .subject(member.getEmail())
                .issuer("todo boot app")
                .issuedAt(new Date())
                .expiration(expireDate)
                .compact();
    }

    public String validateAndGetUsername(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }
}