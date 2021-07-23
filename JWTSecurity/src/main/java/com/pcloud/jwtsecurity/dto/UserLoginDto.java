package com.pcloud.jwtsecurity.dto;

import lombok.Data;

@Data
public class UserLoginDto {
    private String email;
    private String password;

    public UserLoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
