package com.board.board.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostResponse {

    private Long id;
    private String title;
    private String content;
    private Integer viewCount;
    private Long likeCount;
    private LocalDateTime createdAt;
    private String username;
}
