package com.teachAssistantHelper.controller;

import com.teachAssistantHelper.dto.auth.LoginRequestDto;
import com.teachAssistantHelper.dto.auth.LoginResponseDto;
import com.teachAssistantHelper.jwt.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequest, HttpServletResponse response) {
        // 1. 사용자 인증 시도
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        // 2. 인증 성공 시 JWT 생성
        String token = jwtUtil.createToken(authentication.getName());
        // cookie 생성
        Cookie cookie = jwtUtil.createCookie(token);
        response.addCookie(cookie);

        return new LoginResponseDto(token);
    }
}
