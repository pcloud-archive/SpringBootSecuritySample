package com.pcloud.simplespringsecurity.service;

import com.pcloud.simplespringsecurity.domain.CustomUserDetails;
import com.pcloud.simplespringsecurity.domain.User;
import com.pcloud.simplespringsecurity.repository.MemoryUserRepository;
import com.pcloud.simplespringsecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final MemoryUserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User byEmail = userRepository.findByEmail(username);
        return new CustomUserDetails(byEmail);
    }

    public UserDetails login(Authentication authentication) throws UsernameNotFoundException {
        String email = (String)authentication.getPrincipal();
        String password = (String)authentication.getCredentials();
        User user = userRepository.findByEmailEndPassword(email, password);
        return new CustomUserDetails(user);
    }
}
