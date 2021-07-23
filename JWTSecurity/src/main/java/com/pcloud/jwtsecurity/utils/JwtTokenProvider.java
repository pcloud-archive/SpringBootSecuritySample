package com.pcloud.jwtsecurity.utils;

import com.pcloud.jwtsecurity.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    private final long EXPIRATION_ACCESS_TIME = 60 * 60 * 1;
    private final long EXPIRATION_REFRESH_TIME = 60 * 60 * 24 * 30;

    @PostConstruct
    protected void init() {
        SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
    }

    /**
     * Access Token 생성 함수
     * @param userId
     * @param roles
     * @return
     */
    public String createJwtAccessToken(String userId, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(userId); // JWT Payload에 저장되는 데이터
        claims.put("roles", roles); // 정보는 key /value
        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXPIRATION_ACCESS_TIME);

        // iss: 토큰 발급자 (issuer)
        // sub: 토큰 제목 (subject)
        // aud: 토큰 대상자 (audience)
        // exp: 토큰의 만료 시간 (expiration)
        // nbf: 토큰의 활성 날짜 (Not Before)
        // iat: 토큰이 발급된 시간 (issued at)

        return Jwts.builder()
                .setClaims(claims) // 토큰의 정보가 저장된 객체
                .setIssuedAt(now) // 토큰 발행 일자
                .setExpiration(expiration) // 토큰 유효 기간
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // 사용할 암호화 알고리즘
                .compact();
    }

    /**
     * Refresh Token 생성 함수
     * @param value
     * @return
     */
    public String createJwtRefreshToken(String value) {
        Claims claims = Jwts.claims();
        claims.put("value", value); // 정보는 key /value
        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXPIRATION_REFRESH_TIME);

        return Jwts.builder()
                .setClaims(claims) // 토큰의 정보가 저장된 객체
                .setIssuedAt(now) // 토큰 발행 일자
                .setExpiration(expiration) // 토큰 유효 기간
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // 사용할 암호화 알고리즘
                .compact();
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
