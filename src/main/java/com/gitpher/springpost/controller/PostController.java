package com.gitpher.springpost.controller;

import com.gitpher.springpost.data.dto.PostRequestDto;
import com.gitpher.springpost.data.dto.ResponseDto;
import com.gitpher.springpost.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;


    // 해당 게시글 글쓴이 가져오기
    @PostMapping("/api/auth/post")
    public ResponseDto<?> createPost(@RequestBody PostRequestDto requestDto, @AuthenticationPrincipal User user) {
        String nickname = user.getUsername();
        return postService.createPost(requestDto, nickname);
    }

    @GetMapping("/api/post/{id}")
    public ResponseDto<?> getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    @GetMapping("/api/post")
    public ResponseDto<?> getAllPosts() {
        return postService.getAllPost();
    }

    // 해당 게시물 작성자만 수정 가능하게 하기
    @PutMapping("/api/auth/post/{id}")
    public ResponseDto<?> updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal User user) {
        String nickname = user.getUsername();
        return postService.updatePost(id, postRequestDto, nickname);
    }

    // 해당 게시물 작성자만 삭제 가능하게 하기
    @DeleteMapping("/api/auth/post/{id}")
    public ResponseDto<?> deletePost(@PathVariable Long id, @AuthenticationPrincipal User user) {
        String nickname = user.getUsername();
        return postService.deletePost(id, nickname);
    }

//    @PostMapping("/api/post/{id}")
//    public ResponseDto<?> validateAuthorByPassword(@PathVariable Long id, @RequestBody String password) {
//        return postService.validateAuthorByPassword(id, password);
//    }

}
