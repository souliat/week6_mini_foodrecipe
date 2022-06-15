package com.sparta.foodrecipe.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {
    private String content;
    private String username;
    private Long postId;
}