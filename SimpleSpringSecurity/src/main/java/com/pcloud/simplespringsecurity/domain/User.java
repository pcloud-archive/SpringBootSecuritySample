package com.pcloud.simplespringsecurity.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class User {
    private Long id;
    private String email;
    private String name;
    private String password;
    private String role;
}
