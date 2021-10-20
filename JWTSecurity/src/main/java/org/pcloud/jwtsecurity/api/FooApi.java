package org.pcloud.jwtsecurity.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pcloud.jwtsecurity.jwt.JwtTokenProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * helloFoo는 토큰을 전달받을 수 있으며,
 * byeFoo는 helloFoo에서 전달받은 토큰을 사용해야 접근할 수 있습니다.
 */

@Slf4j
@RequiredArgsConstructor
@RequestMapping("foo")
@RestController
public class FooApi {
    private final JwtTokenProvider jwtTokenProvider;
    @GetMapping("hello")
    public String helloFoo(HttpServletResponse response) {
        log.info("foo hello");
        // 토큰을 생성한다. 권한은 ROLE_FOO 이다.
        String jwtToken = jwtTokenProvider.createJwtToken("Foo", Arrays.asList("ROLE_FOO"));
        response.addHeader("token", jwtToken);
        return "hello Foo!!";
    }

    @GetMapping("bye")
    public String byeFoo() {
        log.info("foo bye");
        return "bye Foo...";
    }
}
