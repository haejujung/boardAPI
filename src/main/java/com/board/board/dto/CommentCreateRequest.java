package com.board.board.dto;

import lombok.Data;

@Data
public class CommentCreateRequest {

    private Long postId;
    private Long userId;
    private String content;

}
