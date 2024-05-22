package com.bit.healthpartnerboot.jwt;

import com.bit.healthpartnerboot.hash.LogoutToken;
import com.bit.healthpartnerboot.hash.RefreshToken;
import com.bit.healthpartnerboot.repository.redis.LogoutTokenRepository;
import com.bit.healthpartnerboot.repository.redis.RefreshTokenRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Component
// @RequiredArgsConstructor
public class JwtTokenProvider {
    private static final long ACCESS_TOKEN_EXPIRATION_MS = 24 * 60 * 60 * 1000;
    private static final long REFRESH_TOKEN_EXPIRATION_MS = 30L * 24 * 60 * 60 * 1000;
    private final RefreshTokenRepository refreshTokenRepository;
    private final LogoutTokenRepository logoutTokenRepository;

    private final SecretKey key;
    private final JwtParser parser;

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey, RefreshTokenRepository refreshTokenRepository, LogoutTokenRepository logoutTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.logoutTokenRepository = logoutTokenRepository;
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.parser = Jwts.parser().setSigningKey(key).build();
    }

    public String createAccessToken(String memberEmail) {
        Date accessTokenExpireDate = Date.from(Instant.now().plus(ACCESS_TOKEN_EXPIRATION_MS, ChronoUnit.MILLIS));
        return createToken(memberEmail, accessTokenExpireDate);
    }

    public void createRefreshToken(String memberEmail) {
        Date refreshTokenExpireDate = Date.from(Instant.now().plus(REFRESH_TOKEN_EXPIRATION_MS, ChronoUnit.MILLIS));

        String refreshToken = createToken(memberEmail, refreshTokenExpireDate);
        RefreshToken redis = new RefreshToken(refreshToken, memberEmail);
        refreshTokenRepository.save(redis);
    }

    public String createToken(String memberEmail, Date tokenExpireDate) {
        return Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)
                .subject(memberEmail)
                .issuer("todo boot app")
                .issuedAt(new Date())
                .expiration(tokenExpireDate)
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

    public boolean validateToken(String token) {
        try {
            parser.parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public boolean isLogout(String token) {
        LogoutToken logoutToken = logoutTokenRepository.findByAccessToken(token);
        return logoutToken != null;
    }

}
