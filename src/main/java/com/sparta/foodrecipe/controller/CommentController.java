package com.sparta.foodrecipe.controller;

import com.sparta.foodrecipe.dto.CommentRequestDto;
import com.sparta.foodrecipe.dto.CommentResponseDto;
import com.sparta.foodrecipe.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // 댓글 작성
    @PostMapping("/api/comments")
    public void postComment(@RequestBody CommentRequestDto commentRequestDto
            //@AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        commentService.postComment(commentRequestDto);
    }

    // 댓글 조회
    @GetMapping("/api/comments/{postId}")
    public List<CommentResponseDto> getComment(@PathVariable Long postId) {

        return commentService.getComment(postId);
    }
}