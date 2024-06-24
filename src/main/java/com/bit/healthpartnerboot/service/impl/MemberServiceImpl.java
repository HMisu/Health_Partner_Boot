package com.bit.healthpartnerboot.service.impl;

import com.bit.healthpartnerboot.dto.MemberDTO;
import com.bit.healthpartnerboot.entity.Member;
import com.bit.healthpartnerboot.entity.MemberHistory;
import com.bit.healthpartnerboot.hash.LogoutToken;
import com.bit.healthpartnerboot.hash.RefreshToken;
import com.bit.healthpartnerboot.jwt.JwtTokenProvider;
import com.bit.healthpartnerboot.repository.jpa.MemberHistoryRepository;
import com.bit.healthpartnerboot.repository.jpa.MemberRepository;
import com.bit.healthpartnerboot.repository.redis.LogoutTokenRepository;
import com.bit.healthpartnerboot.repository.redis.RefreshTokenRepository;
import com.bit.healthpartnerboot.service.EmailService;
import com.bit.healthpartnerboot.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final MemberHistoryRepository memberHistoryRepository;
    private final EmailService emailService;
    private final LogoutTokenRepository logoutTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;

    @Override
    public MemberDTO signUp(MemberDTO memberDTO) {
        float heightInMeters = memberDTO.getHeight() / 100;
        if (heightInMeters > 0) {
            float bmi = memberDTO.getWeight() / (heightInMeters * heightInMeters);
            memberDTO.setBmi(bmi);
        }

        Member member = memberRepository.save(memberDTO.toEntity());

        return member.toDTO();
    }

    @Override
    public MemberDTO signIn(MemberDTO memberDTO) {
        Optional<Member> loginMemberOptional = memberRepository.findByEmail(memberDTO.getEmail());

        if (loginMemberOptional.isEmpty()) {
            throw new RuntimeException("not exist userid");
        }

        Member loginMember = loginMemberOptional.get();

        if (!passwordEncoder.matches(memberDTO.getPassword(), loginMember.getPassword())) {
            throw new RuntimeException("wrong password");
        }

        String accessToken = jwtTokenProvider.createAccessToken(loginMember.getEmail());
        jwtTokenProvider.createRefreshToken(loginMember.getEmail());

        MemberDTO loginMemberDTO = loginMember.toDTO();
        loginMemberDTO.setToken(accessToken);

        return loginMemberDTO;
    }

    @Override
    public MemberDTO findByMember(String email) {
        Optional<Member> loginMemberOptional = memberRepository.findByEmail(email);

        if (loginMemberOptional.isEmpty()) {
            throw new RuntimeException("not exist userid");
        }

        Member loginMember = loginMemberOptional.get();

        return loginMember.toDTO();
    }

    @Override
    public void signOut(String email, String token) {
        LogoutToken logoutToken = new LogoutToken(UUID.randomUUID().toString(), token);
        logoutTokenRepository.save(logoutToken);

        RefreshToken refreshToken = refreshTokenRepository.findByMemberEmail(email);
        refreshTokenRepository.deleteById(refreshToken.getRefreshToken());
    }

    @Override
    public long checkDuplicatedEmail(String email) {
        return memberRepository.countByEmail(email);
    }

    @Override
    public void createEmailAuthCode(String email) {
        Random random = new Random();
        int code = random.nextInt(9000) + 1000;

        String subject = "회원가입 인증 메일입니다.";
        String text = "인증 코드는 " + code + "입니다.";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);

        emailService.saveVerificationCode(email, String.valueOf(code));
    }

    @Override
    public void verificationEmail(String email, String code) {
        String savedCode = emailService.getVerificationCode(email);
        emailService.verificationEmail(code, savedCode);
    }

    @Override
    public void verificationPassword(String email, String password) {
        String enteredPassword = memberRepository.findByEmail(email).get().getPassword();

        boolean isPasswordMatch = passwordEncoder.matches(password, enteredPassword);

        if (!isPasswordMatch) {
            throw new RuntimeException("passwords do not match");
        }
    }

    @Override
    @Transactional
    public void modifyPassword(String email, String password) {
        memberRepository.updatePasswordByEmail(email, password);

    }

    @Override
    @Transactional
    public void modifyProfile(MemberDTO memberDTO) {
        Member member = memberRepository.findByEmail(memberDTO.getEmail()).get();
        member.setName(memberDTO.getName());
        member.setAge(memberDTO.getAge());
        member.setGender(memberDTO.getGender());
        member.setHeight(memberDTO.getHeight());
        member.setWeight(memberDTO.getWeight());
        member.setActivityLevel(memberDTO.getActivityLevel());
        memberRepository.save(member);
    }

    @Override
    @Transactional
    public void modifyProfileImg(String email, String imgAddress) {
        memberRepository.updateImgByEmail(email, imgAddress);
    }

    @Override
    public List<Map<String, Object>> getBmiGraph(String email) {
        List<MemberHistory> histories = memberHistoryRepository.findAllByMember(email);

        Map<String, Object> weightData = Map.of(
                "id", "Weight",
                "color", "hsl(156, 70%, 50%)",
                "data", histories.stream()
                        .map(history -> Map.of(
                                "x", history.getCreatedAt().toLocalDate().format(DateTimeFormatter.ofPattern("M/d")),
                                "y", history.getWeight()
                        ))
                        .collect(Collectors.toList())
        );

        Map<String, Object> heightData = Map.of(
                "id", "Height",
                "color", "hsl(275, 70%, 50%)",
                "data", histories.stream()
                        .map(history -> Map.of(
                                "x", history.getCreatedAt().toLocalDate().format(DateTimeFormatter.ofPattern("M/d")),
                                "y", history.getHeight()
                        ))
                        .collect(Collectors.toList())
        );

        Map<String, Object> bmiData = Map.of(
                "id", "BMI",
                "color", "hsl(116, 70%, 50%)",
                "data", histories.stream()
                        .map(history -> Map.of(
                                "x", history.getCreatedAt().toLocalDate().format(DateTimeFormatter.ofPattern("M/d")),
                                "y", history.getBmi()
                        ))
                        .collect(Collectors.toList())
        );

        return List.of(weightData, heightData, bmiData);
    }

    @Override
    @Transactional
    public void modifyHeightAndWeight(String email, float height, float weight) {
        Member member = memberRepository.findByEmail(email).get();
        member.setHeight(height);
        member.setWeight(weight);
        memberRepository.save(member);
    }

    @Override
    public void deleteMember(String email) {
        memberRepository.deleteByEmail(email);
    }
}
