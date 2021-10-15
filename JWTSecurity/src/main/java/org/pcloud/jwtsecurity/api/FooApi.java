package org.pcloud.jwtsecurity.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("foo")
@RestController
public class FooApi {

    @GetMapping("hello")
    public String helloFoo() {
        return "hello Foo!!";
    }

    @GetMapping("bye")
    public String byeFoo() {
        return "bye Foo...";
    }
}
