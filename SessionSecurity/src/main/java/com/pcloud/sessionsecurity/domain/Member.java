package com.pcloud.sessionsecurity.domain;

import lombok.Getter;

@Getter
public class Member {
    private String email;
    private String name;
    private String password;

    protected Member(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public Member() {
    }

    public static Member generate(String email, String name, String password) {
        return new Member(email, name, password);
    }
}
