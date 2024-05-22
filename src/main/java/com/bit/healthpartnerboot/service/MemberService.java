package com.bit.healthpartnerboot.service;

import com.bit.healthpartnerboot.dto.MemberDTO;

public interface MemberService {
    MemberDTO signUp(MemberDTO memberDTO);

    MemberDTO signIn(MemberDTO memberDTO);

    MemberDTO findByMember(String email);

    void signOut(String email, String token);

    long checkDuplicatedEmail(String email);

    void createEmailAuthCode(String email);

    void verificationEmail(String email, String code);
}
