package com.bit.healthpartnerboot.controller;

import com.bit.healthpartnerboot.dto.MemberDTO;
import com.bit.healthpartnerboot.dto.ResponseDTO;
import com.bit.healthpartnerboot.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody MemberDTO memberDTO) {
        ResponseDTO<MemberDTO> responseDTO = new ResponseDTO<>();

        try {
            memberDTO.setRole("ROLE_USER");
            memberDTO.setLastLoginDate(LocalDateTime.now().toString());
            memberDTO.setPassword(passwordEncoder.encode(memberDTO.getPassword()));

            MemberDTO joinMemberDTO = memberService.join(memberDTO);

            joinMemberDTO.setPassword("");

            responseDTO.setItem(joinMemberDTO);
            responseDTO.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            responseDTO.setErrorCode(100);
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody MemberDTO userDTO) {
        ResponseDTO<MemberDTO> responseDTO = new ResponseDTO<>();

        try {
            MemberDTO loginUserDTO = memberService.login(userDTO);

            loginUserDTO.setPassword("");

            responseDTO.setItem(loginUserDTO);
            responseDTO.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            if (e.getMessage().equalsIgnoreCase("not exist userid")) {
                responseDTO.setErrorCode(200);
                responseDTO.setErrorMessage(e.getMessage());
            } else if (e.getMessage().equalsIgnoreCase("wrong password")) {
                responseDTO.setErrorCode(201);
                responseDTO.setErrorMessage(e.getMessage());
            } else {
                responseDTO.setErrorCode(202);
                responseDTO.setErrorMessage(e.getMessage());
            }

            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @PostMapping("/email-check")
    public ResponseEntity<?> idCheck(@RequestBody MemberDTO userDTO) {
        ResponseDTO<Map<String, String>> responseDTO = new ResponseDTO<>();

        try {
            long emailCheck = memberService.emailCheck(userDTO);

            Map<String, String> returnMap = new HashMap<>();

            if (emailCheck == 0) {
                returnMap.put("emailCheckResult", "available email");
            } else {
                returnMap.put("emailCheckResult", "invalid email");
            }

            responseDTO.setItem(returnMap);
            responseDTO.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setErrorCode(101);
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }
}
