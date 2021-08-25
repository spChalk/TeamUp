package com.example.socialnetworkingapp.model.like;

import com.example.socialnetworkingapp.model.bio.Bio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    @Query("SELECT Like FROM Like WHERE Like.user.id = ?1")
    Optional<List<Like>> findAllByUserId(Long id);

    @Query("DELETE FROM Like WHERE Like.user.id = ?1 AND Like.post.id = ?2")
    void deleteByUserAndPost(Long user_id, Long post_id);
}
