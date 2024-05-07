package com.bit.healthpartnerboot.service.impl;

import com.bit.healthpartnerboot.dto.MemberDTO;
import com.bit.healthpartnerboot.entity.Member;
import com.bit.healthpartnerboot.entity.Tokens;
import com.bit.healthpartnerboot.jwt.JwtTokenProvider;
import com.bit.healthpartnerboot.repository.MemberRepository;
import com.bit.healthpartnerboot.repository.RefreshTokenRepository;
import com.bit.healthpartnerboot.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public MemberDTO join(MemberDTO memberDTO) {
        Member member = memberRepository.save(memberDTO.toEntity());

        return member.toDTO();
    }

    @Override
    public MemberDTO login(MemberDTO memberDTO) {
        Optional<Member> loginMemberOptional = memberRepository.findByEmail(memberDTO.getEmail());

        if (loginMemberOptional.isEmpty()) {
            throw new RuntimeException("존재하지 않는 사용자입니다.");
        }

        Member loginMember = loginMemberOptional.get();

        if (!passwordEncoder.matches(memberDTO.getPassword(), loginMember.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        // Tokens 객체 생성
        Tokens tokens = jwtTokenProvider.create(loginMember);

        // 로그인 멤버 DTO에 토큰 설정
        MemberDTO loginMemberDTO = loginMember.toDTO();
        loginMemberDTO.setToken(tokens.getAccessToken());

        // 로그인 멤버 DTO 반환
        return loginMemberDTO;
    }

    @Override
    public long emailCheck(MemberDTO memberDTO) {
        return memberRepository.countByEmail(memberDTO.getEmail());
    }
}
