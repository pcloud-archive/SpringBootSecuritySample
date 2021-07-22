package com.pcloud.jwtsecurity.repository;

import com.pcloud.jwtsecurity.domain.User;

public interface UserRepository {
    User findByEmail(String email);
    User findByEmailAndPassword(String email, String password);
}
