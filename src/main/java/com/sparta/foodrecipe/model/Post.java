package com.sparta.foodrecipe.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Post extends Timestamped {
    // ID
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name="USER_ID")
    private User user;

    @Column(nullable = false)
    String nickname;

    @Column(nullable = false)
    String title;

    @Column(nullable = false)
    String imageUrl;

    @Column(nullable = false)
    String content;

    @Column(nullable = false)
    Long categoryNum;

    @Column(nullable = false)
    Long likeCount;

    @Column(nullable = false)
    boolean likeByMe;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<Like> likes = new ArrayList<>();



}
