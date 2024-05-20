package com.bit.healthpartnerboot.controller;

import com.bit.healthpartnerboot.dto.MemberDTO;
import com.bit.healthpartnerboot.dto.ResponseDTO;
import com.bit.healthpartnerboot.entity.CustomUserDetails;
import com.bit.healthpartnerboot.jwt.JwtAuthenticationFilter;
import com.bit.healthpartnerboot.jwt.JwtTokenProvider;
import com.bit.healthpartnerboot.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody MemberDTO memberDTO) {
        ResponseDTO<MemberDTO> responseDTO = new ResponseDTO<>();

        try {
            memberDTO.setRole("ROLE_USER");
            memberDTO.setLastLoginDate(LocalDateTime.now().toString());
            memberDTO.setPassword(passwordEncoder.encode(memberDTO.getPassword()));
            memberDTO.setIsActive(true);

            MemberDTO joinMemberDTO = memberService.signUp(memberDTO);

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
    public ResponseEntity<?> signIn(@RequestBody MemberDTO userDTO) {
        ResponseDTO<MemberDTO> responseDTO = new ResponseDTO<>();

        try {
            MemberDTO loginMemberDTO = memberService.signIn(userDTO);

            loginMemberDTO.setPassword("");

            responseDTO.setItem(loginMemberDTO);
            responseDTO.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            if (e.getMessage().equalsIgnoreCase("not exist userid")) {
                responseDTO.setErrorCode(200);
                responseDTO.setErrorMessage(e.getMessage());
            } else if (e.getMessage().equalsIgnoreCase("wrong password")) {
                responseDTO.setErrorCode(201);
                responseDTO.setErrorMessage(e.getMessage());
            } else if (e.getMessage().equalsIgnoreCase("Refresh token is invalid")) {
                responseDTO.setErrorCode(202);
                responseDTO.setErrorMessage(e.getMessage());
            } else if (e.getMessage().equalsIgnoreCase("Access token is invalid")) {
                responseDTO.setErrorCode(202);
                responseDTO.setErrorMessage(e.getMessage());
            } else {
                responseDTO.setErrorCode(203);
                responseDTO.setErrorMessage(e.getMessage());
            }
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @PostMapping("/email-check")
    public ResponseEntity<?> emailCheck(@RequestBody MemberDTO userDTO) {
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

    @GetMapping("/signout")
    public ResponseEntity<?> signOut(HttpServletRequest request, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ResponseDTO<Map<String, String>> responseDTO = new ResponseDTO<>();

        try {
            if (customUserDetails != null) {
                log.info(">>>>>>>>>>> " + customUserDetails.getUsername());
            } else {
                log.info("customUserDetail is null");
            }

            String token = jwtAuthenticationFilter.parseBearerToken(request);

            memberService.signOut(customUserDetails.getUsername(), token);

            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(null);
            SecurityContextHolder.setContext(securityContext);

            Map<String, String> msgMap = new HashMap<>();
            msgMap.put("logoutMsg", "logout success");

            responseDTO.setItem(msgMap);
            responseDTO.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setErrorCode(202);
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @GetMapping("/kakao")
    public ResponseEntity<?> signIn(HttpServletRequest request) {
        ResponseDTO<MemberDTO> responseDTO = new ResponseDTO<>();

        try {
            String token = jwtAuthenticationFilter.parseBearerToken(request);
            String email = jwtTokenProvider.validateAndGetUsername(token);
            MemberDTO loginMemberDTO = memberService.findByMember(email);

            loginMemberDTO.setToken(token);
            loginMemberDTO.setPassword("");

            responseDTO.setItem(loginMemberDTO);
            responseDTO.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            if (e.getMessage().equalsIgnoreCase("not exist userid")) {
                responseDTO.setErrorCode(200);
                responseDTO.setErrorMessage(e.getMessage());
            } else if (e.getMessage().equalsIgnoreCase("wrong password")) {
                responseDTO.setErrorCode(201);
                responseDTO.setErrorMessage(e.getMessage());
            } else if (e.getMessage().equalsIgnoreCase("Refresh token is invalid")) {
                responseDTO.setErrorCode(202);
                responseDTO.setErrorMessage(e.getMessage());
            } else if (e.getMessage().equalsIgnoreCase("Access token is invalid")) {
                responseDTO.setErrorCode(202);
                responseDTO.setErrorMessage(e.getMessage());
            } else {
                responseDTO.setErrorCode(203);
                responseDTO.setErrorMessage(e.getMessage());
            }
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }
}

