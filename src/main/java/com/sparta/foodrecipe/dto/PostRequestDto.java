package com.sparta.foodrecipe.dto;

import com.sparta.foodrecipe.model.Post;
import com.sparta.foodrecipe.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class PostRequestDto {

    private String title;
    private String content;
    private Long categoryId;

//    private User user;

    public PostRequestDto(String title, String content, Long categoryId) {
        this.title = title;
        this.content = content;
        this.categoryId = categoryId;
    }
}
