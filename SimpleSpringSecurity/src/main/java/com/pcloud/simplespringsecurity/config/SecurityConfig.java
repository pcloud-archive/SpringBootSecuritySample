package com.pcloud.simplespringsecurity.config;

import com.pcloud.simplespringsecurity.config.filter.CustomAuthenticationProcessingFilter;
import com.pcloud.simplespringsecurity.config.handler.CustomAuthenticationFailureHandler;
import com.pcloud.simplespringsecurity.config.handler.CustomAuthenticationSuccessHandler;
import com.pcloud.simplespringsecurity.repository.MemoryUserRepository;
import com.pcloud.simplespringsecurity.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/**").hasRole("USER")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .loginProcessingUrl("/login-process")
                .and()
                .addFilterBefore(customAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public CustomAuthenticationProcessingFilter customAuthenticationProcessingFilter() {
        CustomAuthenticationProcessingFilter customAuthenticationProcessingFilter = new CustomAuthenticationProcessingFilter("/login-process");
        customAuthenticationProcessingFilter.setAuthenticationManager(new CustomAuthenticationManager(new UserService(new MemoryUserRepository())));
        customAuthenticationProcessingFilter.setAuthenticationSuccessHandler(new CustomAuthenticationSuccessHandler());
        customAuthenticationProcessingFilter.setAuthenticationFailureHandler(new CustomAuthenticationFailureHandler());
        return customAuthenticationProcessingFilter;
    }
}
