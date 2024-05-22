package com.bit.healthpartnerboot.service;

import com.bit.healthpartnerboot.hash.EmailAuth;
import com.bit.healthpartnerboot.repository.redis.EmailAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final EmailAuthRepository emailAuthRepository;

    public void verificationEmail(String code, String savedCode) {
        if (!code.equals(savedCode)) {
            throw new RuntimeException("email authentication failed");
        }
    }

    public String getVerificationCode(String email) {
        return emailAuthRepository.findByEmail(email).getVerifyCode();
    }

    public void saveVerificationCode(String email, String code) {
        EmailAuth emailAuth = new EmailAuth(email, code);
        emailAuthRepository.save(emailAuth);
    }
}
