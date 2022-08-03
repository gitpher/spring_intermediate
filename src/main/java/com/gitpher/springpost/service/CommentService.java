package com.gitpher.springpost.service;

import com.gitpher.springpost.data.dto.CommentRequestDto;
import com.gitpher.springpost.data.dto.ResponseDto;
import com.gitpher.springpost.data.entity.Comment;
import com.gitpher.springpost.data.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public ResponseDto<?> createComment(CommentRequestDto requestDto) {
        Comment comment = new Comment(requestDto);
        commentRepository.save(comment);
        return ResponseDto.success(comment);
    }

    @Transactional(readOnly = true)
    public ResponseDto<?> getAllComment(Long postId) {
        return ResponseDto.success(commentRepository.findAllByPostIdOrderByModifiedAtDesc(postId));
    }

    @Transactional
    public ResponseDto<?> updateComment(Long id, CommentRequestDto requestDto) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (optionalComment.isEmpty()) {
            return ResponseDto.fail("NULL_COMMENT_ID", "comment id isn't exist");
        }
        Comment comment = optionalComment.get();
        comment.update(requestDto);
        return ResponseDto.success(comment);
    }

    @Transactional
    public ResponseDto<?> deleteComment(Long id) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (optionalComment.isEmpty()) {
            return ResponseDto.fail("NOT_FOUND", "comment id is not exist");
        }
        Comment comment = optionalComment.get();
        commentRepository.delete(comment);
        return ResponseDto.success(true);
    }

    @Transactional(readOnly = true)
    public ResponseDto<?> validateAuthorByPassword(Long id, String password) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (optionalComment.isEmpty()) {
            return ResponseDto.fail("NOT_FOUND", "comment id is not exist");
        }
        Comment comment = optionalComment.get();
        if (comment.getPassword().equals(password)) {
            return ResponseDto.fail("PASSWORD_NOT_CORRECT", "password is not correct");
        }
        return ResponseDto.success(true);
    }



}
