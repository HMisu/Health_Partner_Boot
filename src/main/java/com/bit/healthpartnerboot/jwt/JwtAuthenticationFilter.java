package com.bit.healthpartnerboot.jwt;

import com.bit.healthpartnerboot.entity.Member;
import com.bit.healthpartnerboot.entity.Tokens;
import com.bit.healthpartnerboot.repository.MemberRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    private String parseBearerToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String accessToken = parseBearerToken(request);
            if (accessToken != null && !accessToken.equalsIgnoreCase("null")) {
                // Access Token 유효성 검사
                if (jwtTokenProvider.validateToken(accessToken)) {
                    String refreshToken = request.getHeader("refreshToken");
                    if (refreshToken != null && jwtTokenProvider.validateRefreshToken(refreshToken)) {
                        // Refresh Token이 유효한 경우, 해당하는 Member 정보를 가져옴
                        String username = jwtTokenProvider.extractUsernameFromToken(refreshToken);
                        Member member = memberRepository.findByEmail(username)
                                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
                        // 새로운 액세스 토큰을 생성하여 응답 헤더에 추가
                        Tokens tokens = jwtTokenProvider.create(member);
                        response.setHeader("Authorization", "Bearer " + tokens.getAccessToken());
                    } else {
                        // Refresh Token이 유효하지 않은 경우
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Refresh token is invalid");
                        return;
                    }
                }
            } else if (request.getRequestURI().equals("/logout")) { // 로그아웃 요청이 들어온 경우
                // 로그아웃 처리를 위한 로직 추가
                // 여기서 사용자의 access token과 refresh token을 만료시키거나 삭제합니다.
                // 예를 들어, jwtTokenProvider.expireAccessToken(username) 메소드를 호출하여 access token을 만료시키고,
                // jwtTokenProvider.deleteRefreshToken(refreshToken) 메소드를 호출하여 refresh token을 삭제합니다.

                // 로그아웃이 성공적으로 처리되었다면 응답을 반환하거나 리다이렉트할 수 있습니다.
                response.setStatus(HttpServletResponse.SC_OK);
                return;
            }
        } catch (Exception e) {
            // 예외 처리
            logger.error("Error processing authentication filter: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
