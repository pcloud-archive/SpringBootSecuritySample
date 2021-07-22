package com.pcloud.jwtsecurity.repository;

import com.pcloud.jwtsecurity.domain.User;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Repository
public class MemoryUserRepository implements UserRepository {
    private Map<Long, User> users = new HashMap<>();

    @PostConstruct
    public void init() {
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

        users.put(admin.getId(), admin);
        users.put(user.getId(), user);
    }

    @Override
    public User findByEmail(String email) {
        return users.values()
                .stream()
                .filter(u->u.getEmail().equals(email))
                .findFirst().get();
    }

    @Override
    public User findByEmailAndPassword(String email, String password) {
        return users.values()
                .stream()
                .filter(u-> u.getEmail().equals(email) && u.getPassword().equals(password))
                .findFirst()
                .orElse(User.builder().build());
    }
}
