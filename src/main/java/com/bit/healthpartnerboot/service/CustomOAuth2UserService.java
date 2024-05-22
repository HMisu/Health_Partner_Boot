package com.bit.healthpartnerboot.service;

import com.bit.healthpartnerboot.dto.CustomOAuth2User;
import com.bit.healthpartnerboot.dto.Role;
import com.bit.healthpartnerboot.entity.Member;
import com.bit.healthpartnerboot.oauth2.GoogleResponse;
import com.bit.healthpartnerboot.oauth2.KakaoResponse;
import com.bit.healthpartnerboot.oauth2.OAuth2Response;
import com.bit.healthpartnerboot.repository.jpa.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;
        if (registrationId.equals("kakao")) {
            oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());
        } else if (registrationId.equals("google")) {
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        } else {
            return null;
        }

        Optional<Member> mem = memberRepository.findByEmail(oAuth2Response.getEmail());
        Member member;
        if (mem.isEmpty()) {
            member = registerNewMember(oAuth2Response.getEmail(), oAuth2Response.getName(), oAuth2Response.getImg(), oAuth2Response.getProvider());
        } else {
            memberRepository.updateByOAuth2Info(oAuth2Response.getEmail(), oAuth2Response.getName(), oAuth2Response.getImg());
            member = memberRepository.findByEmail(oAuth2Response.getEmail()).get();
        }

        return new CustomOAuth2User(member.toDTO());
    }

    private Member registerNewMember(String email, String name, String img, String provider) {
        Member member = Member.builder()
                .email(email)
                .name(name)
                .imgAddress(img)
                .role(Role.ROLE_USER)
                .provider(provider)
                .lastLoginDate(LocalDateTime.now())
                .isActive(true)
                .build();
        return memberRepository.save(member);
    }
}