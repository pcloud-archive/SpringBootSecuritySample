package com.pcloud.sessionsecurity.config;

import com.pcloud.sessionsecurity.config.security.filter.CustomAuthenticationFilter;
import com.pcloud.sessionsecurity.config.security.handler.AuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
                .formLogin()
                // 인증에 대해 성공과 실패를 처리할 수 있다.
                // 여기에 Handler 를 설정해도 되고, AuthenticationFilter 에 핸들러로 설정해도 된다.
            .and()
                .addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * AuthenticationFilter를 Bean 객체로 등록
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

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AuthenticationSuccessHandler();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }
}
