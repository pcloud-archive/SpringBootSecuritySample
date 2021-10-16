package org.pcloud.jwtsecurity.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {
    private String SECRET_KEY = "SECRET";
    private final long EXPIRATION_ACCESS_TIME = 60L * 60L;
    private final long EXPIRATION_REFRESH_TIME = 60L * 60L * 24L* 30L; // 60초, 60분, 24시간, 30일

    @PostConstruct // Bean에 등록된 후 별도의 초기화 작업을 위해 선언하는 어노테이션.
    protected void init() {
        SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
    }

    public String createJwtToken(String userId, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(userId); // JWT Payload에 저장되는 데이터
        claims.put("roles", roles); // 정보는 key /value
        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXPIRATION_ACCESS_TIME);

        return Jwts.builder()
                .setClaims(claims) // 토큰의 정보가 저장된 객체
                .setIssuedAt(now) // 토큰 발행 일자
                .setExpiration(expiration) // 토큰 유효 기간
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // 사용할 암호화 알고리즘
                .compact();
    }

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
}
