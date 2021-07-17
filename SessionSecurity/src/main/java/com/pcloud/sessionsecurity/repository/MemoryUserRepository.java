package com.pcloud.sessionsecurity.repository;

import com.pcloud.sessionsecurity.domain.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class MemoryUserRepository implements UserRepository {
    private Map<Long, User> users = new HashMap<>();

    public MemoryUserRepository() {
        User admin = User.builder()
                .id(0L)
                .name("PCloud")
                .email("pcloud@gmail.com")
                .password("pcloud123")
                .role("ROLE_ADMIN")
                .build();

        User user = User.builder()
                .id(1L)
                .name("User1")
                .email("user01@gmail.com")
                .password("qwe123")
                .role("ROLE_USER")
                .build();

        users.put(0L, admin);
        users.put(1L, user);
    }

    @Override
    public User findByEmailAndPassword(String email, String password) {
        return users.values()
                .stream()
                .filter(u->u.getEmail().equals(email) && u.getPassword().equals(password))
                .findFirst()
                .orElse(User.builder().build());
    }
}
