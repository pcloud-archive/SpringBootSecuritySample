package com.pcloud.simplespringsecurity.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class UserDto {
    private String email;
    private String name;
    private String password;

    public UserDto(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }
}
