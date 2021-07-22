package com.pcloud.jwtsecurity.utils;

import com.pcloud.jwtsecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private String SECRET_KEY;
    private final UserService userService;

    @PostConstruct
    protected void init() {
        SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    // JWT 토큰 생성
    public String createToken() {
        return "";
    }
    // JWT 토큰 인증
    // JWT 토큰 정보 가져오기
    public void getUser2Token() {}
    // JWT 토큰 유효성 확인
    public void validateToken() {}
    // JWT 토큰 만료일자 확인
    public void validateDate() {}
    // JWT 토큰 가져오기
    public void resolveToken(HttpServletRequest req) {}
}
