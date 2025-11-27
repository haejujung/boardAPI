package com.board.board.controller;


import com.board.board.dto.CommentCreateRequest;
import com.board.board.dto.CommentResponse;
import com.board.board.dto.CommentUpdateRequest;
import com.board.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public CommentResponse createComment(@RequestBody CommentCreateRequest request){
        return commentService.createComment(request);
    }

    @GetMapping("/post/{postId}")
    public List<CommentResponse> getComments(@PathVariable Long postId){
        return commentService.getComments(postId);
    }

    @PutMapping("/{id}")
    public CommentResponse updateComment(@PathVariable Long id,
            @RequestBody CommentUpdateRequest request){
        return commentService.updateComment(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteComment(@PathVariable Long id){
        commentService.deleteComment(id);
        return "deleted";
    }
}
