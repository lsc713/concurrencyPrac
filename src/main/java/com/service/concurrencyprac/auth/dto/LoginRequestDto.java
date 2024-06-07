package com.service.concurrencyprac.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {

    private String email;
    private String password;

    public LoginRequestDto(){

    }

    public LoginRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
