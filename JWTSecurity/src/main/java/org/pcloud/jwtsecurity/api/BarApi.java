package org.pcloud.jwtsecurity.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("bar")
@RestController
public class BarApi {

    @GetMapping("hello")
    public String helloBar() {
        return "hello Bar!!";
    }

    @GetMapping("bye")
    public String byeBar() {
        return "bye Bar...";
    }
}
