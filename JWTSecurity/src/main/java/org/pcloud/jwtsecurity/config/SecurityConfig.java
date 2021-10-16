package org.pcloud.jwtsecurity.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 서버의 자원(resources)에 대해 접근을 설정.
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
            .ignoring() // 특정 접근 요청을 실패로 처리한다.
            .antMatchers("/v2/api-docs", "/swagger-resources/**",
                "/swagger-ui.html", "/webjars/**", "/swagger/**"); // 실패처리 하고자 하는 접근 경로 ex) /swagger-ul.html
    }

    /**
     * 요청에 대해 접근, 권한, 필터 등 다양한 설정을 작성하는 함수
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
            httpBasic()    // http 인증 기반의 로그인창(id, password)창을 설정한다. (기본적으로 제공된다.)
                .disable() // http 인증 기반의 로그인창을 비활성화한다.

            .csrf()        // csrf는 맨 아래 설명.
                .disable() // csrf 설정을 비활성화한다.

            .sessionManagement() // 세션 정책을 설정한다. (생성 정책에 가깝다)      SS(Security Session)
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)   // SS가 세션을 생성하지 않고, 기존 생성된 세션도 사용하지 않는다.
//              .sessionCreationPolicy(SessionCreationPolicy.NEVER)       // SS가 세션을 생성하지 않고, 기존 생성된 세션은 사용한다.
//              .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // 기본 값. SS 가 세션이 필요할 때 SS가 직접 생성.
//              .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)      // 항상 세션을 허용
            .and()
            .authorizeRequests() // 요청에 대한 인증처리를 설정한다.
            .antMatchers("/foo/hello", "/bar/hello", "/popo/hello") // 처리하고자 하는 요청 경로를 작성한다.
                .permitAll()                                                  // 무조건 접근을 허용한다.

            .antMatchers("/foo/bye") // 처리하고자 하는 요청 경로를 작성한다.
                .hasRole("FOO")                // 역할이 일치하면 접근을 허용 (권한이랑 비슷하고, ROLE_ 패턴이 생략된 형태.)

            .antMatchers("/bar/bye")
                .hasRole("BAR")                // 역할은 jwt토큰에 저장하거나, db에 사용자 정보를 저장할 때 함께 저장하는 방법을 사용한다.

            .antMatchers("/popo/**")
              .authenticated()                       // 인증된 사용자만 접근을 허용한다. 단 상단(43줄)에서 먼저 허용된 /bar/hello 는 접근이 가능하다.
//              .denyAll()                           // 무조건 접근을 허용하지 않는다.
//              .hasAuthority("ROLE_ADMIN")          // 권한이 일치하면 접근을 허용
//              .hasRole("ADMIN")                    // 역할이 일치하면 접근을 허용 (권한이랑 비슷하고, ROLE_ 패턴이 생략된 형태.)
//              .hasAnyRole("ADMIN", "SUPER_ADMIN")  // 역할목록 중 일치하는 것이 있다면 접근을 허용
//              .antMatchers("/foo/**").permitAll()

            .anyRequest()  // 설정되지 않은 모든 요청에 대해 설정한다.
                .permitAll();
    }

    /** csrf (Cross-site request forgery)   CSRF, XSRF 라 표현한다.
     * 웹 사이트의 취약점을 공격하는 하나의 공격기법.
     * 사용자(client)가 웹 브라우저(크롬 등)을 신용하는 상태에 대한 헛점을 공격하는 방법으로, 아래 예시를 서술한다.
     *
     * 일반 사용자가 웹 사이트에 로그인 등을 통해 인증된 상태이다. (인증은 Cookie를 통해 확인이 되며, Cookie는 웹 브라우저에 저장된다.)
     * 해커가 게시글에 공격 스크립트를 등록한다. (공격 스크립트는 다운로드 파일을 업로드하여 사용자가 직접 실행하게 하거나, 게시글 입력창에 직접 작성하는 방법이 있다.)
     * 일반 사용자는 해커가 등록한 게시글을 열람할 시 공격 스크립트가 동작한다. (웹 브라우저는 별도의 설정이 없다면 스크립트를 실행하게 된다.)
     * 이 때 공격 스크립트는 일반 사용자의 브라우저에 저장된 쿠키를 사용하여 서버를 공격하게 된다. 즉 일반 사용자는 자신도 모르게 공격자가 된다.
     *
     * 공격 스크립트마다 결과는 다양하게 된다. 대표적인 공격 방식인 사용자 정보 탈취 공격스크립트를 간략히 설명하면,
     *  1. 사용자의 Cookie를 가져온다.
     *  2. Cookie를 이용해 해당 도메인의 서버에 개인정보 조회를 요청한다.
     *  3. 조회된 개인정보를 해커가 작성해둔 서버 또는 이메일로 전송한다.
     *
     *  방어 방법
     *
     *  Referer Check
     *  요청의 referer를 확인한다. referer에서 요청한 도메인을 검사할 수 있으며, 도메인이 틀릴 시 공격이라 판단하여 제외하는 방어기법이다.
     *  단 요청 중간에 요청문을 변조할 수 있는 프로그램이 있어, 쉽게 방어가 파훼되는 방법이다.
     *
     *  CSRF Token
     *  jwt token과는 다른 방법이며, 리플라이 방어와 비슷한 방식입니다.
     *  사용자의 매 요청마다 Session에 랜덤한 토큰을 저장 후 사용자에게 전달합니다.
     *  이 후 요청마다 전달했던 토큰와 현재 session에 저장된 토큰을 비교하여 같은지 판별하여 방어하는 방법입니다.
     *
     *  요청문 처리
     *  검색을 해도 별다른 설명이 없어서 기억으로 작성합니다. 게시글을 등록할 때 스크립트 패턴으로 저장이 요청되면 예외처리를 하거나, 업로드 파일을 검사할 수 있고,
     *  스크립트 인식을 무력화 하는 방법도 존재합니다.
     * */
}
