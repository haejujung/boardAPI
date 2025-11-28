package com.board.board.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentCreateRequest {

    private Long postId;
    
    @NotBlank(message = "내용을 입력해주세요")
    private String content;

}
