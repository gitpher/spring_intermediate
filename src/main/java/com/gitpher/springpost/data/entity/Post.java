package com.gitpher.springpost.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gitpher.springpost.data.dto.PostRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Post extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private String author;

    public Post(PostRequestDto postRequestDto, String nickname) {
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
        this.author = nickname;
    }

    public void update(PostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
    }

}
