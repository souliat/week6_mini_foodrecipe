package com.sparta.foodrecipe.repository;

import com.sparta.foodrecipe.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Long countByPostId(Long PostId);
    boolean existsByUsernameAndPostId(String username, Long postId);
    void deleteByPostId(Long PostId);
}
