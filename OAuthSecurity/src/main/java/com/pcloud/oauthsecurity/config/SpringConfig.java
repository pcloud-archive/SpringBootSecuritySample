package com.pcloud.oauthsecurity.config;

import com.pcloud.oauthsecurity.MemoryUserRepository;
import com.pcloud.oauthsecurity.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    @Bean
    public UserRepository userRepository() {
        return new MemoryUserRepository();
    }
}
