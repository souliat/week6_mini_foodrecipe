package com.sparta.foodrecipe.repository;

import com.sparta.foodrecipe.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByOrderByCreatedAtDesc();

    List<Post> findAllByCategoryIdOrderByCreatedAtDesc(Long categoryId);
}
