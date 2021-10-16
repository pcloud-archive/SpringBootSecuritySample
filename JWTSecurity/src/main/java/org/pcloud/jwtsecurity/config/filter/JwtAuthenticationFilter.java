package org.pcloud.jwtsecurity.config.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pcloud.jwtsecurity.jwt.JwtTokenProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
//https://minkukjo.github.io/framework/2020/12/18/Spring-142/

/**
 * Filter : 요청받은 Servlet을 확인 및 처리하는 추상(abstract) 클래스.
 * GenericFilterBean : Filter와 동일하나, Spring의 설정 정보를 가져올 수 있는 추상(abstract) 클래스.
 * OncePerRequestFilter : 요청 한번에 대해 Filter를 동작하는 추상(abstract) 클래스. (redirect를 염두한 Filter)
 */
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);

        if(token != null && !token.isEmpty() && jwtTokenProvider.validateToken(token)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder
                    .getContext()
                    .setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
