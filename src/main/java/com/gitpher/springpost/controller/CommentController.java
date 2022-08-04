package com.gitpher.springpost.controller;

import com.gitpher.springpost.data.dto.CommentRequestDto;
import com.gitpher.springpost.data.dto.ResponseDto;
import com.gitpher.springpost.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    // 해당 게시글 글쓴이 가져오기
    @PostMapping("/api/auth/comment")
    public ResponseDto<?> createComment(@RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal User user) {
        String nickname = user.getUsername();
        return commentService.createComment(requestDto, nickname);
    }

    @GetMapping("/api/comment/{postId}")
    public ResponseDto<?> getAllComments(@PathVariable Long postId) {
        return commentService.getAllComment(postId);
    }

    // 해당 댓글 작성자만 수정 가능하게 하기
    @PutMapping("/api/auth/comment/{id}")
    public ResponseDto<?> updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal User user) {
        String nickname = user.getUsername();
        return commentService.updateComment(id, requestDto, nickname);
    }

    // 해당 댓글 작성자만 삭제 가능하게 하기
    @DeleteMapping("/api/auth/comment/{id}")
    public ResponseDto<?> deleteComment(@PathVariable Long id, @AuthenticationPrincipal User user) {
        String nickname = user.getUsername();
        return commentService.deleteComment(id, nickname);
    }

//    @PostMapping("/api/comment/{id}")
//    public ResponseDto<?> validateAuthorByPassword(@PathVariable Long id, @RequestBody String password) {
//        return commentService.validateAuthorByPassword(id, password);
//    }

}
