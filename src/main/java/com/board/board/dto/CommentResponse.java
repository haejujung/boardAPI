package com.board.board.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentResponse {
    private Long id;
    private String username;
    private String content;
    private LocalDateTime createdAt;

}
