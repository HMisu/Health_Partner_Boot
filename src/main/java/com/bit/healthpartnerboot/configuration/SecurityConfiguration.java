package com.bit.healthpartnerboot.configuration;

import com.bit.healthpartnerboot.jwt.JwtAutheticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAutheticationFilter jwtAutheticationFilter;

    // 비밀번호 암호화 객체 bean 객체로 등록
    // 비밀번호 암호화와 UsernamePassworToken의 비밀번호와 CustomUserDetails 객체의 비밀번호를 비교하기 위한 passwordEncoder 객체 생성
    // 암호화된 비밀번호는 다시는 복호화할 수 없다.
    // PasswordEncoder에 있는 matches(암호화되지 않은 비밀번호, 암호화된 비밀번호)메소드를 이용해서 값을 비교한다. 일치하면 true, 일치하지 않으면 false
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(httpSecurityCorsConfigurer -> {

                })
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(httpSecuritySessionManagementConfigurer -> {
                    httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
                    authorizationManagerRequestMatcherRegistry.requestMatchers("/", "/member/**").permitAll();
                    authorizationManagerRequestMatcherRegistry.anyRequest().authenticated();
                })
                // filter 등록
                // cosrFilter 동작 후 jwtAuthenticationFilter가 동작
                .addFilterAfter(jwtAutheticationFilter, CorsFilter.class)
                .build();

    }
}
