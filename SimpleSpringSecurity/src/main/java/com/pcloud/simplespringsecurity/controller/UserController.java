package com.pcloud.simplespringsecurity.controller;

import com.pcloud.simplespringsecurity.dto.UserDto;
import com.pcloud.simplespringsecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("user")
@RestController
public class UserController {

    private final UserService userService;
    @PostMapping("/join")
    public String join(@RequestBody UserDto userDto) {
        return "redirect:/home";
    }
}
