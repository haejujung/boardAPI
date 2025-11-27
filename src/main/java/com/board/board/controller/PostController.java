package com.board.board.controller;

import com.board.board.dto.PostCreateRequest;
import com.board.board.dto.PostResponse;
import com.board.board.dto.PostUpdateRequest;
import com.board.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @PostMapping
    public PostResponse createPost(@RequestBody PostCreateRequest request){

        return postService.createPost(request);
    }

    @GetMapping("/{id}")
    public PostResponse getPost(@PathVariable Long id){
        return postService.getPost(id);
    }

    @PutMapping("/{id}")
    public PostResponse updatePost(@PathVariable Long id,
                                   @RequestBody PostUpdateRequest request){
        return postService.updatePost(id,request);
    }

    @GetMapping
    public List<PostResponse> getAllPosts(){
        return postService.getAllPosts();
    }

    @DeleteMapping("/{id}")
    public String deletePost(@PathVariable Long id){
        postService.deletePost(id);
        return "deleted";
    }

    @PostMapping("/{postId}/like")
    public String likePost(@PathVariable Long postId){
        return postService.likePost(postId);
    }
}
