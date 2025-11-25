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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public CommentResponse createComment(CommentCreateRequest request) {

        User user = userRepository.findById(request.getUserId())
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

        comment.setContent((request.getContent()));

        Comment updated = commentRepository.save(comment);

        return  toResponse(updated);
    }

    public void deleteComment(Long id){
        if(!commentRepository.existsById(id)){
            throw new RuntimeException("Comment not found");
        }
        commentRepository.deleteById(id);
    }
}

