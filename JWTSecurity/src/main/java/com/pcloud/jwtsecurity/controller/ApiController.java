package com.pcloud.jwtsecurity.controller;

import com.pcloud.jwtsecurity.domain.User;
import com.pcloud.jwtsecurity.dto.UserDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

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

    @GetMapping("admin-check")
    public String adminCheck() {
        return "Admin OK";
    }
}
