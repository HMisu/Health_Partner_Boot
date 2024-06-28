package com.bit.healthpartnerboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
                //.allowedOrigins("ec2-3-37-245-8.ap-northeast-2.compute.amazonaws.com:3000")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .allowedHeaders("*")
                //.allowCredentials(true)
                .allowedOriginPatterns("*")
                .exposedHeaders("Authorization")
                .maxAge(3600);
    }
}
