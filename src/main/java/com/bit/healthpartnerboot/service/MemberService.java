package com.bit.healthpartnerboot.service;

import com.bit.healthpartnerboot.dto.MemberDTO;

public interface MemberService {
    MemberDTO signUp(MemberDTO memberDTO);

    MemberDTO signIn(MemberDTO memberDTO);
    
    MemberDTO findByMember(String email);

    void signOut(String email, String token);

    long emailCheck(MemberDTO memberDTO);
}
