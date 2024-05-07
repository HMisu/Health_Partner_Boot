package com.bit.healthpartnerboot.jwt;

import com.bit.healthpartnerboot.entity.Member;
import com.bit.healthpartnerboot.entity.Tokens;
import com.bit.healthpartnerboot.repository.RefreshTokenRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private static final String SECRET_KEY = "Yml0Y2FtcGRldm9wczR0b2RvYm9vdGFwcDUwMm1hZ2ljZWNvbGU=";
    private static final long ACCESS_TOKEN_EXPIRATION_MS = 24 * 60 * 60 * 1000; // 1 day
    private static final long REFRESH_TOKEN_EXPIRATION_MS = 30L * 24 * 60 * 60 * 1000;// 30 days
    private final RefreshTokenRepository refreshTokenRepository;

    // Generate secret key using Keys.hmacShaKeyFor
    SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    // Create JwtParser directly
    JwtParser parser = Jwts.parser().setSigningKey(key).build();

    public Tokens create(Member member) {
        Date accessTokenExpireDate = Date.from(Instant.now().plus(ACCESS_TOKEN_EXPIRATION_MS, ChronoUnit.MILLIS));
        Date refreshTokenExpireDate = Date.from(Instant.now().plus(REFRESH_TOKEN_EXPIRATION_MS, ChronoUnit.MILLIS));

        String accessToken = Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)
                .subject(member.getEmail())
                .issuer("todo boot app")
                .issuedAt(new Date())
                .expiration(accessTokenExpireDate)
                .compact();

        String refreshToken = UUID.randomUUID().toString();

        refreshTokenRepository.saveRefreshToken(refreshToken);

        return new Tokens(accessToken, refreshToken, accessTokenExpireDate, refreshTokenExpireDate);
    }

    public boolean validateRefreshToken(String refreshToken) {
        try {
            Jws<Claims> claims = parser.parseClaimsJws(refreshToken);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public boolean validateToken(String token) {
        try {
            parser.parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String extractUsernameFromToken(String refreshToken) {
        try {
            Jws<Claims> claims = parser.parseClaimsJws(refreshToken);
            return claims.getBody().getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            throw new RuntimeException("Refresh token is invalid");
        }
    }

    public void deleteRefreshToken(String refreshToken) {
        // Logic to delete the refresh token from the database
        refreshTokenRepository.deleteRefreshToken(refreshToken);
    }
}
