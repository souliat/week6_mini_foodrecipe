package com.sparta.foodrecipe.repository;

import com.sparta.foodrecipe.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
