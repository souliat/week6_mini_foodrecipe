package com.sparta.foodrecipe.controller;

import com.sparta.foodrecipe.dto.CommentRequestDto;
import com.sparta.foodrecipe.dto.CommentResponseDto;
import com.sparta.foodrecipe.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/api/comment")
    public void postComment(
            @RequestBody CommentRequestDto commentRequestDto
            //@AuthenticationPrincipal UserDetailsImpl userDetails.getUsername
    ){
        commentService.postComment(commentRequestDto);
    }

    @GetMapping("/api/comments/{postId}")
    public List<CommentResponseDto> getComment(@PathVariable Long postId) throws SQLException
    {
        List<CommentResponseDto> commentResponseDtoList = commentService.getComment(postId);
        return commentResponseDtoList;
    }
}
