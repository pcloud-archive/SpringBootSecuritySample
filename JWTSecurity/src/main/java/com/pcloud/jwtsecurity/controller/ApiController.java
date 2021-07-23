package com.pcloud.jwtsecurity.controller;

import com.pcloud.jwtsecurity.config.filter.JwtAuthenticationFilter;
import com.pcloud.jwtsecurity.domain.User;
import com.pcloud.jwtsecurity.dto.UserDto;
import com.pcloud.jwtsecurity.dto.UserLoginDto;
import com.pcloud.jwtsecurity.dto.UserTokenDto;
import com.pcloud.jwtsecurity.service.UserService;
import com.pcloud.jwtsecurity.utils.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ApiController {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @GetMapping
    public String home() {
        return "Home";
    }

    @GetMapping("info")
    public UserDto userInfo(@AuthenticationPrincipal User user) {
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());

        return userDto;
    }

    @PostMapping("login")
    public UserTokenDto login(@RequestBody UserLoginDto userLoginDto) throws Exception {
        User user = userService.signUp(userLoginDto.getEmail(), userLoginDto.getPassword());
        if(user.getEmail() == null) {
            throw new Exception("");
        }

        // service는 id와 password 검사.
        // 흠 service는 결국 비즈니스 로직의 핵심이다.
        // repository 처럼 낮은 단계의 책임만 갖고 있는게 아닌
        // 보다 구체적인 책임을 지녀야한다.
        // 가령 로그인은 id, password 검사를 하고 일치하면 accessToken과 refreshToken을 생성하고 사용자에게 발급해야한다.
        // 저걸 나눈다??
        // 흠 access, refresh 토큰을 발급하는게 login Api의 목적.

        String token = jwtTokenProvider.createToken(user.getEmail(), Arrays.asList(user.getRole()));

        return new UserTokenDto(user.getEmail(), token);
    }

    @GetMapping("admin-check")
    public String adminCheck() {
        return "Admin OK";
    }
}
