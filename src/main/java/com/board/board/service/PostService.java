package com.board.board.service;

import com.board.board.dto.PostCreateRequest;
import com.board.board.dto.PostResponse;
import com.board.board.model.Post;
import com.board.board.model.User;
import com.board.board.repository.PostRepository;
import com.board.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostResponse createPost(PostCreateRequest request){

        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));

        Post post = new Post();
        post.setUser(user);
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setViewCount(0);

        Post savedPost = postRepository.save(post);

        return toResponse(savedPost);

    }

}
