package com.gitpher.springpost.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {
    private String content;
    private Long postId;
    private String author;
    private String password;
}
