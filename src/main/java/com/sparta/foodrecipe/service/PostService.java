package com.sparta.foodrecipe.service;

import com.sparta.foodrecipe.dto.PostRequestDto;
import com.sparta.foodrecipe.dto.PostResponseDto;
import com.sparta.foodrecipe.model.Post;
import com.sparta.foodrecipe.repository.LikeRepository;
import com.sparta.foodrecipe.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {


    private final S3Service s3service;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository; // LikeCount, LikeByMe 가져와야함.

    // 새로운 글 작성
    public void postSave(PostRequestDto postRequestDto, MultipartFile multipartFile, String dirName) throws IOException {
        String imageUrl = s3service.upload(multipartFile, dirName);
        String username = "test";
        String nickname = "test";

        Post post = new Post(postRequestDto, username, nickname, imageUrl);

        postRepository.save(post);
    }

    // 전체 글 조회
    public List<PostResponseDto> getPosts() {
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();
        List<PostResponseDto> responseDtoList = new ArrayList<>();

        for (Post post : posts) {
            PostResponseDto responseDto = new PostResponseDto(post);

            Long likeCount = likeRepository.countByPostId(post.getId());
            boolean likeByMe = likeRepository.existsByUsernameAndPostId(post.getUsername(), post.getId());

            responseDto.setLikeCount(likeCount);
            responseDto.setLikeByMe(likeByMe);

            responseDtoList.add(responseDto);
        }
        return responseDtoList;
    }

    public List<PostResponseDto> getCategorizedPosts(Long categoryId) {
        List<Post> posts = postRepository.findAllByCategoryIdOrderByCreatedAtDesc(categoryId);
        List<PostResponseDto> responseDtoList = new ArrayList<>();

        for (Post post : posts) {
            PostResponseDto responseDto = new PostResponseDto(post);

            Long likeCount = likeRepository.countByPostId(post.getId());
            boolean likeByMe = likeRepository.existsByUsernameAndPostId(post.getUsername(), post.getId());

            responseDto.setLikeCount(likeCount);
            responseDto.setLikeByMe(likeByMe);

            responseDtoList.add(responseDto);
        }
        return responseDtoList;
    }
}
