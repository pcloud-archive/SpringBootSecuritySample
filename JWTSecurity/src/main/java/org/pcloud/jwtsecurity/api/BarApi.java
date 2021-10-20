package org.pcloud.jwtsecurity.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pcloud.jwtsecurity.jwt.JwtTokenProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * helloBar는 토큰을 전달받을 수 있으며,
 * byeBar는 helloBar에서 전달받은 토큰을 사용해야 접근할 수 있습니다.
 */
@RequiredArgsConstructor
@Slf4j
@RequestMapping("bar")
@RestController
public class BarApi {
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("hello")
    public String helloBar(HttpServletResponse response) {

        log.info("bar hello");
        String jwtToken = jwtTokenProvider.createJwtToken("Bar", Arrays.asList("ROLE_BAR"));
        response.addHeader("token", jwtToken);
        return "hello Bar!!";
    }

    @GetMapping("bye")
    public String byeBar() {

        log.info("bar bye");

        return "bye Bar...";
    }
}
