package com.pcloud.sessionsecurity.repository;

import com.pcloud.sessionsecurity.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class MemoryUserRepositoryTest {
    @Autowired UserRepository userRepository;
    
    /*
        writer: PCloud
        date: 2021-07-16
        desc: 유저 로그인 성공
    */
    @Test
    @DisplayName("유저로그인_성공")
    public void 유저로그인_성공() throws Exception {
        User user = userRepository.findByEmailAndPassword("user01@gmail.com", "qwe123");

        Assertions.assertTrue(user.getEmail() != null);
    }

    /*
        writer: PCloud
        date: 2021-07-16
        desc: 유저 로그인 패스워드 실패
    */
    @Test
    @DisplayName("유저로그인_실패")
    public void 유저로그인_실패() throws Exception {
        User user = userRepository.findByEmailAndPassword("user01@gmail.com", "aaa");
        Assertions.assertTrue(user.getEmail() == null);
    }
}