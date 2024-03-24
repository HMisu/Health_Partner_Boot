package com.bit.healthpartnerboot.service.impl;

import com.bit.healthpartnerboot.dto.MemberDTO;
import com.bit.healthpartnerboot.entity.Member;
import com.bit.healthpartnerboot.jwt.JwtTokenProvider;
import com.bit.healthpartnerboot.repository.MemberRepository;
import com.bit.healthpartnerboot.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Override
    public MemberDTO join(MemberDTO memberDTO) {
        Member member = memberRepository.save(memberDTO.toEntity());

        return member.toDTO();
    }

    @Override
    public MemberDTO login(MemberDTO memberDTO) {
        Optional<Member> loginMember = memberRepository.findByEmail(memberDTO.getEmail());

        if (loginMember.isEmpty()) {
            throw new RuntimeException("not exist userid");
        }
        if (!passwordEncoder.matches(memberDTO.getPassword(), loginMember.get().getPassword())) {
            throw new RuntimeException("wrong password");
        }

        MemberDTO loginMemberDTO = loginMember.get().toDTO();

        loginMemberDTO.setToken(jwtTokenProvider.create(loginMember.get()));

        return loginMemberDTO;
    }

    @Override
    public long emailCheck(MemberDTO memberDTO) {
        return memberRepository.countByEmail(memberDTO.getEmail());
    }
}
