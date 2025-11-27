package com.board.board.service;

import com.board.board.dto.CommentCreateRequest;
import com.board.board.dto.CommentResponse;
import com.board.board.dto.CommentUpdateRequest;
import com.board.board.model.Comment;
import com.board.board.model.Post;
import com.board.board.model.User;
import com.board.board.repository.CommentRepository;
import com.board.board.repository.PostRepository;
import com.board.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public CommentResponse createComment(CommentCreateRequest request) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Comment comment = new Comment();
        comment.setUser(user);
        comment.setPost(post);
        comment.setContent(request.getContent());

        Comment saved = commentRepository.save(comment);

        return toResponse(saved);

    }


    private CommentResponse toResponse(Comment comment) {
        CommentResponse response = new CommentResponse();
        response.setId(comment.getId());
        response.setUsername(comment.getUser().getUsername());
        response.setPostId(comment.getPost().getId());
        response.setContent(comment.getContent());
        response.setCreatedAt(comment.getCreatedAt());
        return response;
    }

    public List<CommentResponse> getCommentsByPostId(Long postId) {

        List<Comment> comments = commentRepository.findByPostId(postId);

        return comments.stream()
                .map(this::toResponse)
                .toList();
    }

    public CommentResponse updateComment(Long id, CommentUpdateRequest request) {

        Comment comment = commentRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Comment not found"));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        if(!comment.getUser().getUsername().equals(username)){
            throw new RuntimeException("작성자만 수정할 수 있습니다");
        }


        comment.setContent((request.getContent()));
        Comment updated = commentRepository.save(comment);

        return  toResponse(updated);
    }

    public void deleteComment(Long id){

        Comment comment = commentRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Comment not found"));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        if(!comment.getUser().getUsername().equals(username)){
            throw new RuntimeException("작성자만 삭제할 수 있습니다");
        }


        commentRepository.deleteById(id);
    }

    public List<CommentResponse> getComments(Long postId){

        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new RuntimeException("Post not found"));

        List<Comment> comments = post.getComments();

        return comments.stream()
                .map(this::toResponse)
                .toList();
    }


}

