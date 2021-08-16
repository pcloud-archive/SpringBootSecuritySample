package com.pcloud.oauthsecurity.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcloud.oauthsecurity.JwtToken;
import com.pcloud.oauthsecurity.JwtTokenProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public OAuth2SuccessHandler(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String token = generateToken(authentication);
        JwtToken jwtToken = new JwtToken(token, "empty");

        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        writer.print(objectMapper.writeValueAsString(jwtToken));
        writer.flush();
    }

    private String generateToken(Authentication authentication) {
        OAuth2User principal = (OAuth2User) authentication.getPrincipal();
        String attributeKey = principal.getName();

        List<String> roles = principal.getAuthorities()
                .stream()
                .map(o -> ((GrantedAuthority) o).getAuthority())
                .collect(Collectors.toList());

        return jwtTokenProvider.createToken(attributeKey, roles);
    }
}
