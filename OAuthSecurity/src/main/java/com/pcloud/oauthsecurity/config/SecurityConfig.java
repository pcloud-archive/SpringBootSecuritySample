package com.pcloud.oauthsecurity.config;
import com.pcloud.oauthsecurity.CustomOAuth2UserService;
import com.pcloud.oauthsecurity.JwtTokenProvider;
import com.pcloud.oauthsecurity.filter.JwtAuthFilter;
import com.pcloud.oauthsecurity.handler.OAuth2FailHandler;
import com.pcloud.oauthsecurity.handler.OAuth2SuccessHandler;
import lombok.AllArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@AllArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomOAuth2UserService customOAuth2UserService;

    public OAuth2SuccessHandler successHandler() {
        return new OAuth2SuccessHandler(jwtTokenProvider);
    }

    public OAuth2FailHandler failHandler() {
        return new OAuth2FailHandler();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**",
                "/swagger-ui.html", "/webjars/**", "/swagger/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //http 방식의 접근을 차단
        http.httpBasic().disable()
                .csrf().disable() //csrf X
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                    .antMatchers("/api/**").hasRole("USER")
                    .anyRequest().authenticated()
                .and()
                .oauth2Login().loginPage("/login")
                .successHandler(successHandler())
                .failureHandler(failHandler())
                .userInfoEndpoint()
                .userService(customOAuth2UserService);
        http.addFilterBefore(new JwtAuthFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
    }
}
