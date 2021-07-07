package com.pcloud.sessionsecurity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/templates/**");
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                // 인가할 요청을 설정
                .authorizeRequests()
                // antMatchers에 포함된 url에 대해 인증을 요구하지 않음.
                .antMatchers("/home/**", "/member/**").permitAll()
                // 나머지 모든 요청에 대해 인증을 요구함.
                .anyRequest().authenticated()
            .and()
                // 로그인 form 설정. 별도의 설정을 하지 않으면 기본 로그인 폼을 제공한다.
            .formLogin();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }
}
