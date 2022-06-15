package com.sparta.foodrecipe.service;

import com.sparta.foodrecipe.dto.CommentRequestDto;
import com.sparta.foodrecipe.dto.CommentResponseDto;
import com.sparta.foodrecipe.model.Comment;
import com.sparta.foodrecipe.model.Post;
import com.sparta.foodrecipe.model.User;
import com.sparta.foodrecipe.repository.CommentRepository;
import com.sparta.foodrecipe.repository.PostRepository;
import com.sparta.foodrecipe.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository,
                          PostRepository postRepository,
                          UserRepository userRepository
    ) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public void postComment(CommentRequestDto commentRequestDto){
        //Test Code
        String username = "test";
        String nickname = "test";
        String password = "1234";

        User user2 = new User();

        user2.setUsername(username);
        user2.setNickname(nickname);
        user2.setPassword(password);

        userRepository.save(user2);


//        String title = "test";
//        String content = "test";
//        String categoryId = "k";
//
//        Post post2 = new Post();
//        post2.setUsername("username");
//        post2.setNickname("nickname");
//        post2.setTitle(title);
//        post2.setContent(content);
//        post2.setCategoryId(categoryId);
//        post2.setImageUrl("image");
//        post2.setLikeCount(2L);
//        post2.setLikeByMe(true);
//
//        postRepository.save(post2);

        User user = userRepository.findById(commentRequestDto.getUserId()).orElseThrow(()
                -> new RuntimeException("회원 정보가 없습니다"));
        Post post = postRepository.findById(commentRequestDto.getPostId()).orElseThrow(()
                -> new RuntimeException("포스트 아이디를 찾을 수 없습니다."));


        //Comment comment = new Comment(commentRequestDto, user);
        Comment comment  = new Comment(commentRequestDto, user);
        comment.setPost(post);
        commentRepository.save(comment);
    }

    public List<CommentResponseDto> getComment(Long postId){
        List<Comment> commentList = commentRepository.findAllByPostId(postId);
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();

        if(commentList.size() == 0){
            throw new RuntimeException("찾는 정보가 없습니다.");
        }
        for(Comment comment : commentList){
            CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
            commentResponseDtoList.add(commentResponseDto);
        }
        return commentResponseDtoList;
    }
}
