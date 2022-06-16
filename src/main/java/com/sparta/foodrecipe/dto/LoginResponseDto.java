package com.sparta.foodrecipe.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class LoginResponseDto {

    boolean result;
    String errorMsg;
    String token;

    String username;

    public LoginResponseDto(boolean result, String errorMsg) {
        this.result = result;
        this.errorMsg = errorMsg;
    }

    public LoginResponseDto(boolean result, String errorMsg, String token, String username) {
        this.result = result;
        this.errorMsg = errorMsg;
        this.token = token;
        this.username = username;
    }

}
