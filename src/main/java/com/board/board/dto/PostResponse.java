package com.board.board.dto;

import com.board.board.model.Post;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
public class PostResponse {

    private Long id;
    private String title;
    private String content;
    private Integer viewCount;
    private Long likeCount;
    private LocalDateTime createdAt;
    private String username;

    public static PostResponse fromEntity(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .viewCount(post.getViewCount())
                .likeCount((long) post.getPostLike().size())
                .createdAt(post.getCreatedAt())
                .username(post.getUser().getUsername())
                .build();
    }

}
