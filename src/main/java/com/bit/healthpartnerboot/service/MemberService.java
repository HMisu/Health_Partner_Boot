package com.bit.healthpartnerboot.service;

import com.bit.healthpartnerboot.dto.MemberDTO;

import java.util.List;
import java.util.Map;

public interface MemberService {
    MemberDTO signUp(MemberDTO memberDTO);

    MemberDTO signIn(MemberDTO memberDTO);

    MemberDTO findByMember(String email);

    void signOut(String email, String token);

    long checkDuplicatedEmail(String email);

    void createEmailAuthCode(String email);

    void verificationEmail(String email, String code);

    void verificationPassword(String email, String password);

    void modifyPassword(String email, String password);

    void modifyProfile(MemberDTO memberDTO);

    void modifyProfileImg(String email, String imgAddress);

    List<Map<String, Object>> getBmiGraph(String email);

    void modifyHeightAndWeight(String email, float height, float weight);

    void deleteMember(String email);
}
