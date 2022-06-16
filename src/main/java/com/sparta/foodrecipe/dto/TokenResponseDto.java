package com.sparta.foodrecipe.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TokenResponseDto {

    boolean result;
    String errorMsg;
    String payload;

    public TokenResponseDto(boolean result, String errorMsg) {
        this.result = result;
        this.errorMsg = errorMsg;
    }


    public TokenResponseDto(boolean result, String payload, String errorMsg) {
        this.result = result;
        this.payload = payload;
        this.errorMsg = errorMsg;
    }
}
