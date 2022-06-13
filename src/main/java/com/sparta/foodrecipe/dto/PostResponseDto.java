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
    private String imageUrl;
    private Long likeCount;
    private boolean likeByMe;
    private LocalDateTime createdAt;


    public PostResponseDto(Post post) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.nickname = post.getNickname();
        this.imageUrl = post.getImageUrl();
//        this.likeCount = post.getLikeCount();
//        this.likeByMe = post.isLikeByMe();
        this.createdAt = post.getCreatedAt();
    }
}
