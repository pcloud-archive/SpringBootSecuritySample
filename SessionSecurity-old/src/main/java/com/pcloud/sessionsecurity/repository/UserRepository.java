package com.pcloud.sessionsecurity.repository;

import com.pcloud.sessionsecurity.domain.User;

public interface UserRepository {
    User findByEmailAndPassword(String email, String password);
}
