package com.sparta.foodrecipe.dto;

import com.sparta.foodrecipe.model.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostResponseDto {
    private Long postId;
    private String title;
    private String nickname;
    private String username;
    private String imageUrl;
    private Long likeCount;
    private boolean likeByMe;
    private LocalDateTime createdAt;
    private String content;


    // 전체 페이지 조회를 위한 생성자
    public PostResponseDto(Post post) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.nickname = post.getNickname();
        this.username = post.getUsername();
        this.imageUrl = post.getImageUrl();
        this.createdAt = post.getCreatedAt();
        this.content = post.getContent(); // 혹시 모르니까 내려 줌.
    }
}
