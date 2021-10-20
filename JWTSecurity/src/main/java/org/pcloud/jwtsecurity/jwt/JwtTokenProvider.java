package org.pcloud.jwtsecurity.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.pcloud.jwtsecurity.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {
    private String SECRET_KEY = "SECRET";
    private final long EXPIRATION_ACCESS_TIME = 60L * 60L * 60L;
    private final long EXPIRATION_REFRESH_TIME = 60L * 60L * 24L* 30L; // 60초, 60분, 24시간, 30일

    @PostConstruct // Bean에 등록된 후 별도의 초기화 작업을 위해 선언하는 어노테이션.
    protected void init() {
        SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
    }

    /**
     * token을 생성하는 메서드
     * @param userId 사용자 id. 꼭 필수사항은 아니며, 원하는 값을 서비스에 맞추어 설정하면 된다.
     * @param roles 권한. ROLE_ 형식으로 전달 받은 값들이다.
     * @return 토큰
     */
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

    /** HttpServletRequest 객체에서 토큰을 조회하기 위한 메서드
     * @param request 요청 Servlet
     * @return 토큰 or null
     */
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("token");
    }

    /**
     * token의 유효(형식, 시간)을 검증하는 메서드
     * @param token 토큰
     * @return true - 유효 / false - 유효하지 않음
     */
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch(Exception e) {
            return false;
        }
    }

    public Authentication getAuthentication(String token) {
        Claims body = Jwts.parser().setSigningKey(SECRET_KEY)
                .parseClaimsJws(token).getBody();
        User user = new User(body.getSubject(), (List<String>) body.get("roles"));
        return new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
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
