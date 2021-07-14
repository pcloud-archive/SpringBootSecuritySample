package com.pcloud.simplespringsecurity.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api")
@Controller
public class ApiController {

    @GetMapping("/home")
    public String main() {
        log.info("API");
        return "apiPage";
    }
}
