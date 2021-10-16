package org.pcloud.jwtsecurity.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("popo")
@RestController
public class PoPoApi {
    @GetMapping("hello")
    public String helloPoPo() {

        log.info("popo hello");

        return "hello PoPo!!";
    }

    @GetMapping("bye")
    public String byePoPo() {

        log.info("popo bye");

        return "bye PoPo...";
    }

    @GetMapping("test")
    public String testPoPo() {

        log.info("popo test");

        return "test PoPo...";
    }
}
