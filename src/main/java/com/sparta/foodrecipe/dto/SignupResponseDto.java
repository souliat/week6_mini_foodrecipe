package com.sparta.foodrecipe.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SignupResponseDto {
    boolean result;
    String errorMsg;

public SignupResponseDto(boolean result, String errorMsg) {
    this.result = result;
    this.errorMsg = errorMsg;
}
}
