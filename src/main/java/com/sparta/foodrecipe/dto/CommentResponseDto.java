package com.sparta.foodrecipe.dto;

import com.sparta.foodrecipe.model.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CommentResponseDto {
    private String nickname;
    private String content;
    private LocalDateTime createAt;

    public CommentResponseDto(Comment comment){
        this.nickname = comment.getNickname();
        this.content = comment.getContent();
        this.createAt = comment.getCreatedAt();
    }
}
