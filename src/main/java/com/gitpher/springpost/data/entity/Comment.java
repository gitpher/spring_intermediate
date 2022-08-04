package com.gitpher.springpost.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gitpher.springpost.data.dto.CommentRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @JsonIgnore
    @JsonProperty("post_id")
    @Column(nullable = false)
    private Long postId;


    public Comment(CommentRequestDto commentRequestDto, String nickname) {
        this.author = nickname;
        this.content = commentRequestDto.getContent();
        this.postId = commentRequestDto.getPostId();
    }

    public void update(CommentRequestDto commentRequestDto) {
        this.content = commentRequestDto.getContent();
        this.postId = commentRequestDto.getPostId();
    }


}
