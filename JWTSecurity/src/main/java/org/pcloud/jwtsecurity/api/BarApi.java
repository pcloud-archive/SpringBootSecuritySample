package org.pcloud.jwtsecurity.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * helloBar는 토큰을 전달받을 수 있으며,
 * byeBar는 helloBar에서 전달받은 토큰을 사용해야 접근할 수 있습니다.
 */

@Slf4j
@RequestMapping("bar")
@RestController
public class BarApi {

    @GetMapping("hello")
    public String helloBar() {

        log.info("bar hello");

        return "hello Bar!!";
    }

    @GetMapping("bye")
    public String byeBar() {

        log.info("bar bye");

        return "bye Bar...";
    }
}
