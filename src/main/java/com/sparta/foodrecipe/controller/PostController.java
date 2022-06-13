package com.sparta.foodrecipe.controller;

import com.sparta.foodrecipe.dto.PostRequestDto;
import com.sparta.foodrecipe.dto.PostResponseDto;
import com.sparta.foodrecipe.model.Post;
import com.sparta.foodrecipe.service.PostService;
import com.sparta.foodrecipe.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class PostController {

    private final PostService postService;

    private final S3Service s3Service;
    @Autowired
    public PostController(PostService postservice, S3Service s3Service) {
        this.postService = postservice;
        this.s3Service = s3Service;
    }


    // 새로운 글 작성
    @PostMapping("/api/posts")
    public void postSave(@RequestParam("image")MultipartFile multipartFile,
                                   @RequestParam("title") String title,
                                   @RequestParam("content") String content,
                                   @RequestParam("categoryNum") Long categoryId) throws IOException {

        PostRequestDto postRequestDto = new PostRequestDto(title, content, categoryId);
        postService.postSave(postRequestDto, multipartFile, "static");
    }

    // 전체 글 조회
    @GetMapping("/api/posts")
    public List<PostResponseDto> totalPosts() {
        return postService.getPosts();
    }

    // 특정 카테고리 별 조회
    @GetMapping("/api/posts/{categoryId}")
    public List<PostResponseDto> categorizedPosts(@PathVariable Long categoryId) {
        System.out.println(categoryId);
        return postService.getCategorizedPosts(categoryId);
    }






//    @PostMapping("/api/images")
//    public String uploadTest(@RequestParam("image") MultipartFile multipartFile) throws IOException{
//        return s3Service.upload(multipartFile, "static");
//
//    }
}
