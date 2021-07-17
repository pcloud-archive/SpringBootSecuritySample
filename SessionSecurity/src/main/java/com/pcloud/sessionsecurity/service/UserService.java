package com.pcloud.sessionsecurity.service;

import com.pcloud.sessionsecurity.domain.CustomUserDetails;
import com.pcloud.sessionsecurity.domain.User;
import com.pcloud.sessionsecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserDetails SignUp(String email, String password) throws UsernameNotFoundException {
        User user = userRepository.findByEmailAndPassword(email, password);
        if(user.getEmail() == null) {
            throw new UsernameNotFoundException("로그인 실패");
        }
        return new CustomUserDetails(user);
    }
}
