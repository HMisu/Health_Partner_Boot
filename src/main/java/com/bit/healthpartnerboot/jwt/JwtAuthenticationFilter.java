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
            if (path.equals("/member/email") || path.equals("/member/email/verify") || path.matches("/food/search") || path.matches("/todo/check")) {
                filterChain.doFilter(request, response);
                return;
            }

            String token = parseBearerToken(request);

            if (token == null || token.equalsIgnoreCase("null") || jwtTokenProvider.isLogout(token)) {
                filterChain.doFilter(request, response);
                return;
            }
            
            if (jwtTokenProvider.validateToken(token)) {
                String email = jwtTokenProvider.validateAndGetUsername(token);
                String refreshToken = refreshTokenRepository.findByMemberEmail(email).getRefreshToken();

                if (jwtTokenProvider.validateToken(refreshToken)) {
                    String newAccessToken = jwtTokenProvider.createAccessToken(email);
                    response.setHeader("Authorization", newAccessToken);

                    UserDetails userDetails = userDetailsService.loadUserByUsername(email);

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
        } catch (Exception e) {
            log.error("Error during JWT authentication", e);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized: " + e.getMessage());
            return;
        }

        filterChain.doFilter(request, response);
    }
}
