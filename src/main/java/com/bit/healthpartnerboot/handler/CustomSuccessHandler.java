package com.bit.healthpartnerboot.handler;

import com.bit.healthpartnerboot.dto.CustomOAuth2User;
import com.bit.healthpartnerboot.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtTokenProvider jwtTokenProvider;

    public CustomSuccessHandler(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String requestURI = request.getRequestURI();
        if ("/oauth2/kakao/signin".equals(requestURI)) {
            return;
        }

        if ("/oauth2/google/signin".equals(requestURI)) {
            return;
        }

        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getName();

        String accessToken = jwtTokenProvider.createAccessToken(email);
        jwtTokenProvider.createRefreshToken(email);

        response.setHeader("Authorization", "Bearer " + accessToken);
        response.sendRedirect("http://54.180.62.153/oauth/redirected?token=" + accessToken);
        // response.sendRedirect("http://ec2-54-180-62-153.ap-northeast-2.compute.amazonaws.com:3000/oauth/redirected?token=" + accessToken);
        // response.sendRedirect("http://localhost:3000/oauth/redirected?token=" + accessToken);
    }
}
