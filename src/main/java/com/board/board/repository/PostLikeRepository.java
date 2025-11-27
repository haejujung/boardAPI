package com.board.board.repository;

import com.board.board.model.Post;
import com.board.board.model.PostLike;
import com.board.board.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike,Long> {

    Optional<PostLike> findByPostAndUser(Post post, User user);

    Long countByPost(Post post);
}
