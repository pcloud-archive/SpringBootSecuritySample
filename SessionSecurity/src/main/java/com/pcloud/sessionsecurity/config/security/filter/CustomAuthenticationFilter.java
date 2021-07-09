package com.pcloud.sessionsecurity.config.security.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * UsernamePasswordAuthenticationFilter 를 직접 상속받아서 구현하는 방법과 AbstractAuthenticationProcessingFilter 를 상속받는 방법이 있습니다.
 * UsernamePasswordAuthentication Token 을 발급할 클래스.
 */
@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("//================Attempt================//");
        log.info(request.getParameter("email"));
        log.info(request.getParameter("password"));

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(
                        request.getParameter("email"),
                        request.getParameter("password"));

        setDetails(request, authToken);
        return this.getAuthenticationManager().authenticate(authToken);
//        return super.attemptAuthentication(request, response);
    }
}
