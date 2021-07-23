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

        String token = jwtTokenProvider.createToken(user.getEmail(), Arrays.asList(user.getRole()));

        return new UserTokenDto(user.getEmail(), token);
    }

    @GetMapping("admin-check")
    public String adminCheck() {
        return "Admin OK";
    }
}
