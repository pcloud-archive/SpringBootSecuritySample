package com.pcloud.jwtsecurity.service;

import com.pcloud.jwtsecurity.domain.User;
import com.pcloud.jwtsecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public User getUserInfo(String email) {
        return userRepository.findByEmail(email);
    }

    public User signUp(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }
}
