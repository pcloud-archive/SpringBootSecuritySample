package com.pcloud.sessionsecurity.config;

import com.pcloud.sessionsecurity.config.security.filter.CustomAuthenticationFilter;
import com.pcloud.sessionsecurity.config.security.handler.CustomAuthenticationFailureHandler;
import com.pcloud.sessionsecurity.config.security.handler.CustomAuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity // Security 활성화 Annotation. (@Configuration 을 갖고 있으므로, 별도로 작성할 필요가 없다.)
// WebSecurityConfigurerAdapter 의 상위 클래스는 Provider 객체를 관리하는 AuthenticationManager 이다.
// 이를 상속받아 직접 구현한 Provider 를 AuthenticationManager 에 등록할 수 있다.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * WebSecurity 객체를 이용하여 정적 자원에 대해 접근권한을 설정할 콜백함수.
     * 정확히는 SpringSecurity 앞단의 설정을 하는 객체이므로, HttpSecurity 의 설정이 Override 될 수 있다.
     * 기본적으로 정적자원은 ADMIN 권한을 갖고 있는 사용자만 접근이 가능하다.
     * 모든 설정을 ignoring 할 경우 ADMIN 권한 없이 접근이 가능해진다.
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/templates/**");
        super.configure(web);
    }

    /**
     * SpringSecurity 의 핵심.
     * HttpSecurity 객체를 이용하여 요청에 대해 접근권한 및 인증을 설정할 콜백함수.
     * - Login 설정 (로그인 페이지, 로그아웃 페이지, 로직을 설정)
     * - 각 요청에 대한 권한을 확인
     * - 인증로직에 관한 커스텀 설정
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // UsernamePasswordAuthenticationFilter 이전에 동작하도록 설정
        // customAuthenticationFilter 가 추가되었다 해서 UsernamePasswordAuthenticationFilter 가 override된 것은 아니다.
        // customAuthenticationFilter 에서 인증상태가 될 경우 UsernamePasswordAuthenticationFilter 는 로직을 통과하는 구조.
        http.addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * AuthenticationFilter를 Bean 객체로 등록
     * configure에서
     * @return
     * @throws Exception
     */
    @Bean
    public CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager());
        // 지정한 URL 요청에 대해 인증 로직을 처리할 것을 명시
        // 명시한 주소로 들어가면 formLogin 쪽으로 접근한다고 생각하면 된다.
        customAuthenticationFilter.setFilterProcessesUrl("/member/login");

        // 인증에 성공했을 때 호출할 SuccessHandler 지정
        customAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler());

        customAuthenticationFilter.afterPropertiesSet();
        return customAuthenticationFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    @Bean
    public CustomAuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    @Bean
    public CustomAuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }
}
