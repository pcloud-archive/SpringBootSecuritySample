package com.pcloud.sessionsecurity.config;

import com.pcloud.sessionsecurity.exception.PatternMissMatchException;
import com.pcloud.sessionsecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.regex.Pattern;

@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationManager implements AuthenticationManager {
    private final UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = (String)authentication.getPrincipal();
        String password = (String)authentication.getCredentials();

        // Email 패턴 안맞을 때 Exception
        if(Pattern.matches("^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$",
                email) == false) {
            throw new PatternMissMatchException("이메일 형식이 맞지 않습니다.");
        }

        // 유저 없을 때 Exception
        UserDetails userDetails = userService.SignUp(email, password);
        if(userDetails.getUsername() == null) {
            throw new AuthenticationCredentialsNotFoundException("로그인 실패");
        }

        return new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(),
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );
    }
}
