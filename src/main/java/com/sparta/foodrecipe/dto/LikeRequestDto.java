package com.sparta.foodrecipe.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeRequestDto {
    private String username;
    private String action;
}
