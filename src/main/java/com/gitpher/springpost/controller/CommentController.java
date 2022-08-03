package com.gitpher.springpost.controller;

import com.gitpher.springpost.data.dto.CommentRequestDto;
import com.gitpher.springpost.data.dto.ResponseDto;
import com.gitpher.springpost.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/api/comment")
    public ResponseDto<?> createComment(@RequestBody CommentRequestDto requestDto) {
        return commentService.createComment(requestDto);
    }

    @GetMapping("/api/comment/{postId}")
    public ResponseDto<?> getAllComments(@PathVariable Long postId) {
        return commentService.getAllComment(postId);
    }

    @PutMapping("/api/comment/{id}")
    public ResponseDto<?> updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto) {
        return commentService.updateComment(id, requestDto);
    }

    @DeleteMapping("/api/comment/{id}")
    public ResponseDto<?> deleteComment(@PathVariable Long id) {
        return commentService.deleteComment(id);
    }

    @PostMapping("/api/comment/{id}")
    public ResponseDto<?> validateAuthorByPassword(@PathVariable Long id, @RequestBody String password) {
        return commentService.validateAuthorByPassword(id, password);
    }

}
