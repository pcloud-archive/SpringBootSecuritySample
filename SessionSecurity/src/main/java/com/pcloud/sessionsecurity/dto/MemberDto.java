package com.pcloud.sessionsecurity.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class MemberDto {
    private String email;
    private String name;
    private String password;

    public MemberDto(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }
}
