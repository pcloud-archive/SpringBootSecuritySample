package com.pcloud.simplespringsecurity.repository;

import com.pcloud.simplespringsecurity.domain.User;

public interface UserRepository {
    User findByEmail(String email);
    User findByEmailAndPassword(String email, String password);
}
