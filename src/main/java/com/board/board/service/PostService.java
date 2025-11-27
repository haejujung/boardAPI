package com.board.board.service;

import com.board.board.dto.PostCreateRequest;
import com.board.board.dto.PostResponse;
import com.board.board.dto.PostUpdateRequest;
import com.board.board.model.Post;
import com.board.board.model.PostLike;
import com.board.board.model.User;
import com.board.board.repository.PostLikeRepository;
import com.board.board.repository.PostRepository;
import com.board.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    
    private User getLoginUser(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(()-> new RuntimeException("유저가 존재하지 않습니다"));
        
    }

    public PostResponse createPost(PostCreateRequest request) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new RuntimeException("User not found"));

        Post post = new Post();
        post.setUser(user);
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setViewCount(0);

        Post savedPost = postRepository.save(post);

        return toResponse(savedPost);
    }

        private PostResponse toResponse(Post post){
            PostResponse response = new PostResponse();
            response.setId(post.getId());
            response.setTitle(post.getTitle());
            response.setContent(post.getContent());
            response.setViewCount(post.getViewCount());
            response.setCreatedAt(post.getCreatedAt());
            response.setUsername(post.getUser().getUsername());
            return response;
        }

    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() ->new RuntimeException("post not found"));

        post.setViewCount(post.getViewCount() + 1);
        Post updated = postRepository.save(post);

        return toResponse(updated);
    }

    public PostResponse updatePost(Long id, PostUpdateRequest request){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Post post = postRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Post not found"));

        if(!post.getUser().getUsername().equals(username)){
            throw new RuntimeException("수정 권한 없음");
        }

        post.setTitle((request.getTitle()));
        post.setContent((request.getContent()));

        Post updated = postRepository.save(post);
        return toResponse(updated);
    }

    public List<PostResponse> getAllPosts(){
        List<Post> posts = postRepository.findAll();

        return posts.stream()
                .map(this::toResponse)
                .toList();
    }

    public void deletePost(Long id){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Post post = postRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("post not found"));

        if(!post.getUser().getUsername().equals(username)){
            throw  new RuntimeException("삭제 권한 없음");
        }
        postRepository.delete(post);
    }

    public String likePost(Long postId){

        User loginUser = getLoginUser();

        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new RuntimeException("게시글이 존재하지 않습니다"));

        Optional<PostLike> existingLike = postLikeRepository.findByPostAndUser(post,loginUser);

        if(existingLike.isPresent()){
            postLikeRepository.delete(existingLike.get());
            return "unliked";

        } else {
            PostLike like = new PostLike();
            like.setPost(post);
            like.setUser(loginUser);
            postLikeRepository.save(like);
            return "liked";
        }
    }


}


