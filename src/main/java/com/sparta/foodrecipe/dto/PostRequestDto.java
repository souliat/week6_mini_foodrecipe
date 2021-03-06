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
    private String categoryId;

//    private User user;

    // 글 등록을 위한 생성자
    public PostRequestDto(String title, String content, String categoryId) {
        this.title = title;
        this.content = content;
        this.categoryId = categoryId;
    }

    // 글 수정을 위한 생성자
    public PostRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
