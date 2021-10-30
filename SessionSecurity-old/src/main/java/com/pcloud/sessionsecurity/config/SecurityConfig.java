package com.pcloud.sessionsecurity.config;

import com.pcloud.sessionsecurity.config.filter.CustomAuthenticationProcessingFilter;
import com.pcloud.sessionsecurity.config.handler.CustomAuthenticationFailureHandler;
import com.pcloud.sessionsecurity.config.handler.CustomAuthenticationSuccessHandler;
import com.pcloud.sessionsecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/test/user").hasAnyRole("USER","ADMIN")
                .antMatchers("/test/admin").hasRole("ADMIN")
                .antMatchers("/test/fail").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .usernameParameter("email")
                .passwordParameter("password")
                .loginProcessingUrl("/login-process")
                .and()
                .addFilterBefore(customAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public CustomAuthenticationProcessingFilter customAuthenticationProcessingFilter() {
        CustomAuthenticationProcessingFilter customAuthenticationProcessingFilter = new CustomAuthenticationProcessingFilter("/login-process");
        CustomAuthenticationManager customAuthenticationManager = new CustomAuthenticationManager(userService);
        CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler = new CustomAuthenticationSuccessHandler();
        CustomAuthenticationFailureHandler customAuthenticationFailureHandler = new CustomAuthenticationFailureHandler();

        customAuthenticationProcessingFilter.setAuthenticationManager(customAuthenticationManager);
        customAuthenticationProcessingFilter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler);
        customAuthenticationProcessingFilter.setAuthenticationFailureHandler(customAuthenticationFailureHandler);

        return customAuthenticationProcessingFilter;
    }
}
