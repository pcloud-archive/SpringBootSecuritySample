package com.pcloud.simplespringsecurity.repository;

import com.pcloud.simplespringsecurity.domain.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class MemoryUserRepository implements UserRepository {
    private Map<Long, User> users = new HashMap();

    public MemoryUserRepository() {
        User build = User.builder().id(0L).name("PCloud").email("pcloud63514@gmail.com").password("qwenm1823").role("ROLE_USER").build();
        users.put(0L, build);
    }

    @Override
    public User findByEmail(String email) {
        return users.values()
                .stream()
                .filter(u->u.getEmail().equals(email))
                .findAny()
                .orElse(User.builder().build());
    }
}
