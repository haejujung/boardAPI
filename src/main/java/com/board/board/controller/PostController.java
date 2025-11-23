package com.board.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    @PostMapping
    public String createPost(){
        return "created";
    }

    @GetMapping("/{id}")
    public String getPost(@PathVariable Long id){
        return "one post";
    }
}
