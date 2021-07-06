package com.pcloud.sessionsecurity.repository.member;

import com.pcloud.sessionsecurity.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    void join(String email, String name, String password);
    Member findByEmail(String email);
    List<Member> findByName(String name);
    List<Member> findAll();
}
