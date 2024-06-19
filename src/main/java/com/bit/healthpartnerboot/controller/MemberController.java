package com.bit.healthpartnerboot.controller;

import com.bit.healthpartnerboot.dto.EmailAuthDTO;
import com.bit.healthpartnerboot.dto.MemberDTO;
import com.bit.healthpartnerboot.dto.ResponseDTO;
import com.bit.healthpartnerboot.entity.CustomUserDetails;
import com.bit.healthpartnerboot.jwt.JwtAuthenticationFilter;
import com.bit.healthpartnerboot.jwt.JwtTokenProvider;
import com.bit.healthpartnerboot.service.MemberService;
import com.bit.healthpartnerboot.service.S3Uploader;
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
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
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
    private final S3Uploader s3Uploader;

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

    @GetMapping("/signout")
    public ResponseEntity<?> signOut(HttpServletRequest request,
                                     @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ResponseDTO<Map<String, String>> responseDTO = new ResponseDTO<>();

        try {
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

    @GetMapping("/signin/oauth")
    public ResponseEntity<?> signInOAuth(HttpServletRequest request) {
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

    @PostMapping("/email")
    public ResponseEntity<?> sendEmail(@RequestBody Map<String, String> requestBody) {
        ResponseDTO<Map<String, String>> responseDTO = new ResponseDTO<>();

        try {
            String email = requestBody.get("email");
            long emailCheck = memberService.checkDuplicatedEmail(email);

            Map<String, String> returnMap = new HashMap<>();

            if (emailCheck == 0) {
                returnMap.put("emailCheckResult", "available email");
            } else {
                returnMap.put("emailCheckResult", "invalid email");
            }

            memberService.createEmailAuthCode(email);

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

    @GetMapping("/email/verify")
    public ResponseEntity<?> verifyEmail(@RequestBody EmailAuthDTO emailAuthDTO) {
        ResponseDTO<Map<String, String>> responseDTO = new ResponseDTO<>();

        try {
            Map<String, String> returnMap = new HashMap<>();

            memberService.verificationEmail(emailAuthDTO.getEmail(), emailAuthDTO.getVerifyCode());

            responseDTO.setItem(returnMap);
            responseDTO.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            if (e.getMessage().equalsIgnoreCase("email authentication failed")) {
                responseDTO.setErrorCode(102);
                responseDTO.setErrorMessage(e.getMessage());
            }
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @GetMapping("/bmi")
    public ResponseEntity<?> getBmiGraph(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ResponseDTO<Map<String, Object>> responseDTO = new ResponseDTO<>();

        try {
            List<Map<String, Object>> historyData = memberService.getBmiGraph(customUserDetails.getUsername());
            responseDTO.setItems(historyData);
            responseDTO.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setErrorCode(101);
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ResponseDTO<MemberDTO> responseDTO = new ResponseDTO<>();

        try {
            MemberDTO memberDTO = memberService.findByMember(customUserDetails.getUsername());
            memberDTO.setPassword("");

            responseDTO.setItem(memberDTO);
            responseDTO.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            responseDTO.setErrorCode(100);
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @PutMapping("/pw/modify")
    public ResponseEntity<?> modifyPassword(@RequestBody MemberDTO memberDTO) {
        ResponseDTO<Map<String, String>> responseDTO = new ResponseDTO<>();

        try {
            Map<String, String> returnMap = new HashMap<>();

            memberService.verificationPassword(memberDTO.getEmail(), memberDTO.getCurrentPassword());
            memberService.modifyPassword(memberDTO.getEmail(), passwordEncoder.encode(memberDTO.getPassword()));
            memberDTO.setPassword("");

            responseDTO.setItem(returnMap);
            responseDTO.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            if (e.getMessage().equalsIgnoreCase("passwords do not match")) {
                responseDTO.setErrorCode(300);
                responseDTO.setErrorMessage(e.getMessage());
            }

            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @PutMapping("/profile/modify")
    public ResponseEntity<?> modifyProfile(@RequestBody MemberDTO memberDTO) {
        ResponseDTO<Map<String, String>> responseDTO = new ResponseDTO<>();

        try {
            Map<String, String> returnMap = new HashMap<>();

            memberService.modifyProfile(memberDTO);

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

    @PutMapping("/img/modify")
    public ResponseEntity<?> modifyProfileImg(@RequestParam("image") MultipartFile file,
                                              @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ResponseDTO<Map<String, String>> responseDTO = new ResponseDTO<>();

        try {
            if (file.isEmpty()) {
                throw new RuntimeException("파일이 비어 있습니다.");
            }

            String savedImgAddress = s3Uploader.upload(file);
            memberService.modifyProfileImg(customUserDetails.getUsername(), savedImgAddress);

            Map<String, String> returnMap = new HashMap<>();

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

    @PutMapping("/height-weight/modify")
    public ResponseEntity<?> modifyHeightAndWeight(@RequestBody MemberDTO memberDTO) {
        ResponseDTO<Map<String, String>> responseDTO = new ResponseDTO<>();

        try {
            Map<String, String> returnMap = new HashMap<>();

            memberService.modifyHeightAndWeight(memberDTO.getEmail(), memberDTO.getHeight(), memberDTO.getWeight());

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

