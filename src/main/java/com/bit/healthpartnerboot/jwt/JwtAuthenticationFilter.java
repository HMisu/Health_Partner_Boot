package com.bit.healthpartnerboot.jwt;

import com.bit.healthpartnerboot.repository.redis.RefreshTokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;

    public String parseBearerToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String path = request.getRequestURI();

            if (path.equals("/member/email") || path.equals("/member/email/verify")) {
                filterChain.doFilter(request, response);
                return;
            }

            // 토큰 값이 있으면 토큰 값이 담기고
            // 없으면 null이 담긴다.
            String token = parseBearerToken(request);
            log.info("Parsed Token: {}", token);
            // access 토큰 블랙리스트에 있는지 검사
            // 이미 로그아웃한 토큰인지
            if (jwtTokenProvider.isLogout(token)) {
                throw new RuntimeException("access token is expired");
            }

            // 토큰 검사 및 security context 등록
            if (token != null && !token.equalsIgnoreCase("null")) {
                if (jwtTokenProvider.validateToken(token)) {
                    String email = jwtTokenProvider.validateAndGetUsername(token);
                    log.info("Validated Email: {}", email);

                    String refreshToken = refreshTokenRepository.findByMemberEmail(email).getRefreshToken();
                    log.info("Retrieved Refresh Token: {}", refreshToken);

                    if (jwtTokenProvider.validateToken(refreshToken)) {
                        String newAccessToken = jwtTokenProvider.createAccessToken(email);
                        response.setHeader("Authorization", newAccessToken);
                        log.info("New Access Token: {}", newAccessToken);
                        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                        // 유효성 검사 완료된 토큰 시큐리티에 인증된 사용자로 등록
                        AbstractAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities()
                        );

                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContext securityContext = SecurityContextHolder.getContext();
                        securityContext.setAuthentication(authenticationToken);
                        SecurityContextHolder.setContext(securityContext);
                    } else {
                        refreshTokenRepository.deleteByMemberEmail(email);
                        throw new RuntimeException("Refresh token is invalid");
                    }
                }
            }
        } catch (Exception e) {
            log.info("error log={}", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}
