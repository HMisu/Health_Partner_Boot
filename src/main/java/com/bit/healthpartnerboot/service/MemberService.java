package com.bit.healthpartnerboot.service;

import com.bit.healthpartnerboot.dto.MemberDTO;

public interface MemberService {
    MemberDTO join(MemberDTO memberDTO);

    MemberDTO login(MemberDTO memberDTO);

    long emailCheck(MemberDTO memberDTO);
}
