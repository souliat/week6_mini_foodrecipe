package com.sparta.foodrecipe.controller;

import com.sparta.foodrecipe.dto.PostRequestDto;
import com.sparta.foodrecipe.dto.PostResponseDto;
import com.sparta.foodrecipe.model.Post;
import com.sparta.foodrecipe.service.PostService;
import com.sparta.foodrecipe.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
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
    public void postSave(@RequestParam("image") MultipartFile multipartFile,
                                   @RequestParam("title") String title,
                                   @RequestParam("content") String content,
                                   @RequestParam("category") String categoryId) throws IOException {

        PostRequestDto postRequestDto = new PostRequestDto(title, content, categoryId);
        postService.postSave(postRequestDto, multipartFile, "static");
    }

    // 게시글 수정
    @PutMapping("/api/posts/{postId}")
    public Post postUpdate(@RequestParam("image")MultipartFile multipartFile,
                           @RequestParam("title") String title,
                           @RequestParam("content") String content,
                           @PathVariable Long postId) throws IOException {

        PostRequestDto postRequestDto = new PostRequestDto(title, content);
        String dirName = "static";
        return postService.postUpdate(postRequestDto, multipartFile, postId, dirName);
    }

    // 게시글 삭제
    @DeleteMapping("/api/posts/{postId}")
    public Long postDelete(@PathVariable Long postId) {
        return postService.deletePost(postId);
    }

    // 전체 글 조회
    @GetMapping("/api/posts")
    public List<PostResponseDto> totalPosts() {
        return postService.getPosts();
    }

    // 특정 카테고리 별 조회
    @GetMapping("/api/posts/{categoryId}")
    public List<PostResponseDto> categorizedPosts(@PathVariable String categoryId) {
        System.out.println(categoryId);
        return postService.getCategorizedPosts(categoryId);
    }

    // 상세 페이지 조회
    @GetMapping("/api/posts/detail/{postId}")
    public PostResponseDto detailPost(@PathVariable Long postId) {

        return postService.getDetail(postId);
    }






//    @PostMapping("/api/images")
//    public String uploadTest(@RequestParam("image") MultipartFile multipartFile) throws IOException{
//        return s3Service.upload(multipartFile, "static");
//
//    }
}
