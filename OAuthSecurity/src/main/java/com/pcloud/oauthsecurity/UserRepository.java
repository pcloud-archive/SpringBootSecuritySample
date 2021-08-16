package com.pcloud.oauthsecurity;


import java.util.List;
import java.util.Optional;

public interface UserRepository {
    public Long save(User user);
    public Optional<User> findOne(Long id);
    public List<User> findAll();
    public Optional<User> findByEmail(String email);
}
