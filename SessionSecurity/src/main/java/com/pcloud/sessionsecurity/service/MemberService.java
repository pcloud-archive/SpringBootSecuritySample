package com.pcloud.sessionsecurity.service;

import com.pcloud.sessionsecurity.domain.Member;
import com.pcloud.sessionsecurity.exception.EmailDuplicateException;
import com.pcloud.sessionsecurity.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public void join(String email, String name, String password) {
        validateDuplicateMember(email);
        memberRepository.join(email, name, password);
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public List<Member> findsByName(String name) {
        return memberRepository.findByName(name);
    }

    private void validateDuplicateMember(String email) {
        if(findByEmail(email).getEmail() != null) {
            throw new EmailDuplicateException();
        }
    }
}
