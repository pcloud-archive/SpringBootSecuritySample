package com.pcloud.oauthsecurity.filter;

import com.pcloud.oauthsecurity.JwtTokenProvider;
import com.pcloud.oauthsecurity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthFilter extends GenericFilterBean {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 헤더에서 JWT 가져옴
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        //유효성 검증
        if(token != null && jwtTokenProvider.validateToken(token)) {
            User user = User.builder()
                    .id(1)
                    .email("pcloud63514@gamil.com")
                    .role("ROLE_USER")
                    .build();
            //토큰에서 사용자 정보를 받아옴
            // SecurityContext에서 Authentication 객체를 저장
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);

        }
        chain.doFilter(request, response);
    }
}
