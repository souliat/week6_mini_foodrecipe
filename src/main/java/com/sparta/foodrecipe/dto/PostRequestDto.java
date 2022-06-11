package com.sparta.foodrecipe.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequestDto {

    private String title;
    private String imageUrl;
    private String content;
    private Long categoryNum;

}
