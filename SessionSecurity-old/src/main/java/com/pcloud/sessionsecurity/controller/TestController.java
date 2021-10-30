package com.pcloud.sessionsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("test")
@RestController
public class TestController {

    @GetMapping("/user")
    public String UserAccess() {
        return "UserAccess Success";
    }

    @GetMapping("/admin")
    public String AdminAccess() {
        return "AdminAccess Success";
    }

    @GetMapping("/fail")
    public String accessFail() {
        return "Access Fail";
    }
}
