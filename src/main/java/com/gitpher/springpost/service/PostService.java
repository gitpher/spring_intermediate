package com.gitpher.springpost.service;

import com.gitpher.springpost.data.dto.PostRequestDto;
import com.gitpher.springpost.data.dto.ResponseDto;
import com.gitpher.springpost.data.entity.Post;
import com.gitpher.springpost.data.repository.CommentRepository;
import com.gitpher.springpost.data.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public ResponseDto<?> createPost(PostRequestDto requestDto, String nickname) {
        Post post = new Post(requestDto, nickname);
        postRepository.save(post);
        return ResponseDto.success(post);
    }

    @Transactional(readOnly = true)
    public ResponseDto<?> getPost(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isEmpty()) {
            return ResponseDto.fail("NULL_POST_ID", "post id isn't exist");
        }
        return ResponseDto.success(optionalPost.get());
    }

    @Transactional(readOnly = true)
    public ResponseDto<?> getAllPost() {
        return ResponseDto.success(postRepository.findAllByOrderByModifiedAtDesc());
    }

    @Transactional
    public ResponseDto<Post> updatePost(Long id, PostRequestDto requestDto, String nickname) {
        Optional<Post> optionalPost = postRepository.findById(id);

        if (optionalPost.isEmpty()) {
            return ResponseDto.fail("NULL_POST_ID", "post id isn't exist");
        }
        // 해당 게시물의 작성자가 nickname이 맞는지 확인
        if (!optionalPost.get().getAuthor().equals(nickname)) {
            return ResponseDto.fail("NOT_POST_AUTHOR", "작성자만 수정할 수 있습니다.");
        }
        Post post = optionalPost.get();
        post.update(requestDto);
        return ResponseDto.success(post);
    }

    @Transactional
    public ResponseDto<?> deletePost(Long id, String nickname) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isEmpty()) {
            return ResponseDto.fail("NOT_FOUND", "post id is not exist");
        }
        // 해당 게시물의 작성자가 nickname이 맞는지 확인
        if (!optionalPost.get().getAuthor().equals(nickname)) {
            return ResponseDto.fail("NOT_POST_AUTHOR", "작성자만 삭제할 수 있습니다.");
        }

        // 해당 게시글에 달린 댓글 삭제
        Long postId = optionalPost.get().getId();
        commentRepository.deleteAllByPostId(postId);

        // 게시물 삭제
        Post post = optionalPost.get();
        postRepository.delete(post);

        return ResponseDto.success(true);
    }

//    @Transactional(readOnly = true)
//    public ResponseDto<?> validateAuthorByPassword(Long id, String password) {
//        Optional<Post> optionalPost = postRepository.findById(id);
//        if (optionalPost.isEmpty()) {
//            return ResponseDto.fail("NOT_FOUND", "post id is not exist");
//        }
//        Post post = optionalPost.get();
//        if (post.getPassword().equals(password)) {
//            return ResponseDto.fail("PASSWORD_NOT_CORRECT", "password is not correct");
//        }
//        return ResponseDto.success(true);
//    }

}
