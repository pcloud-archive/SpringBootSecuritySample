package com.pcloud.jwtsecurity.dto;

import lombok.Data;

@Data
public class UserTokenDto {
    private String email;
    private String token;

    public UserTokenDto(String email, String token) {
        this.email = email;
        this.token = token;
    }
}
