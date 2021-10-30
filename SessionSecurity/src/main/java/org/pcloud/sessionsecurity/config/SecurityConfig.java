package org.pcloud.sessionsecurity.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Filter 인증을 제외할 리소스 작성하는 함수.
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring() // 리소스 접근을 필터에서 제외한다.
                .antMatchers("/v2/api-docs", "/swagger-resources/**",
                        "/swagger-ui.html", "/webjars/**", "/swagger/**"); // 필터 처리를 무시하고자 하는 경로 ex) /swagger-ul.html
    }

    /**
     * 요청에 대해 접근, 권한, 필터 등 다양한 설정을 작성하는 함수
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .httpBasic()    // http 인증 기반의 로그인창(id, password)창을 설정한다. (기본적으로 제공된다.)
                .disable() // http 인증 기반의 로그인창을 비활성화한다.
            .csrf()        // csrf는 맨 아래 설명.
                .disable() // csrf 설정을 비활성화한다.
        

        super.configure(http);
    }
}
