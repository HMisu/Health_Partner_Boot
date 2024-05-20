package com.bit.healthpartnerboot.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {
    private final MemberDTO memberDTO;

    public CustomOAuth2User(MemberDTO memberDTO) {
        this.memberDTO = memberDTO;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return Map.of();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getName() {
        return memberDTO.getEmail();
    }

    public String getNickName() {
        return memberDTO.getName();
    }
}
