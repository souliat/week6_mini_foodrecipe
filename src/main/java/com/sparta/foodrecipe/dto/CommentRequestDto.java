package com.sparta.foodrecipe.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {
    private String contents;
    private Long userId;
    private Long postId;
}
