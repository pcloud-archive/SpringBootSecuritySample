package com.pcloud.simplespringsecurity.controller;

import com.pcloud.simplespringsecurity.dto.UserDto;
import com.pcloud.simplespringsecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("user")
@Controller
public class UserController {

    private final UserService userService;
    @PostMapping("/signup")
    public String signUp(@ModelAttribute UserDto userDto) {
        log.info("UserDto_NAME:" + userDto.getName());

        return "redirect:/";
    }

    @GetMapping("/signup")
    public String signUp() {
        return "signUp";
    }
}
