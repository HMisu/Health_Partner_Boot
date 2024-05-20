package com.bit.healthpartnerboot.service.impl;

import com.bit.healthpartnerboot.dto.MemberDTO;
import com.bit.healthpartnerboot.entity.CustomUserDetails;
import com.bit.healthpartnerboot.entity.Member;
import com.bit.healthpartnerboot.repository.jpa.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> userOptional = memberRepository.findByEmail(username);

        if (userOptional.isEmpty()) {
            return null;
        } else if (!userOptional.get().getIsActive()) {
            throw new RuntimeException("inActive");
        }

        MemberDTO loginUser = userOptional.get().toDTO();
        loginUser.setLastLoginDate(LocalDateTime.now().toString());

        Member member = loginUser.toEntity();
        memberRepository.save(member);

        memberRepository.flush();

        return CustomUserDetails.builder()
                .member(member)
                .build();
    }
}
