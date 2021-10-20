package org.pcloud.jwtsecurity.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pcloud.jwtsecurity.jwt.JwtTokenProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("popo")
@RestController
public class PoPoApi {
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("hello")
    public String helloPoPo(HttpServletResponse response) {

        log.info("popo hello");
        String jwtToken = jwtTokenProvider.createJwtToken("PoPo", Arrays.asList("ROLE_POPO"));
        response.addHeader("token", jwtToken);

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
