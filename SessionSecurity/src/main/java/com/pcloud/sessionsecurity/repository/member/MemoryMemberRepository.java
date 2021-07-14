package com.pcloud.sessionsecurity.repository.member;

import com.pcloud.sessionsecurity.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class MemoryMemberRepository implements MemberRepository {
    Map<String, Member> members = new HashMap<>();

    @Override
    public void join(String email, String name, String password) {
        log.info("//===Join Try===//");
        log.info(email);
        log.info(name);
        log.info(password);

        if (members.get(email) == null) {
            members.put(email, Member.generate(email, name, password));
        }
    }

    @Override
    public Member findByEmail(String email) {
        return Optional.ofNullable(members.get(email)).orElseGet(Member::new);
    }

    @Override
    public List<Member> findByName(String name) {
        return members.values()
                .stream()
                .filter(m -> m.getName().equals(name))
                .collect(Collectors.toList());
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList(members.values());
    }
}
